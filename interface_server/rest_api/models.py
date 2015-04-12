import datetime
import json
import urlparse

from django.db import models
from thrift.protocol import TBinaryProtocol
from thrift.transport import TTransport

import common.model.ttypes as model


class Error(Exception):
    pass


class GenerationStore(models.Model):
    generation_ser = models.BinaryField()


class ArtifactUrlQuery(models.Model):
    page_uri = models.URLField(primary_key=True, db_index=True, unique=True)
    generation_id = models.IntegerField()
    artifact_idx = models.IntegerField()


def canonical_uri(uri):
    res = urlparse.urlparse(uri)
    rev_domain = '.'.join(reversed(res.hostname.split('.')))
    return urlparse.urlunparse(('', rev_domain, res.path, res.params, res.query, res.fragment))[2:]

def artifact_exists_by_page_uri(page_uri):
    try:
        ArtifactUrlQuery.objects.get(page_uri=canonical_uri(page_uri))
        return True
    except ArtifactUrlQuery.DoesNotExist as e:
        return False


def mark_artifact_as_existing(generation, artifact):
    artifact_idx = 0
    for a in generation.artifacts:
        if a.page_uri == artifact.page_uri:
           break
        artifact_idx += 1

    url_query = ArtifactUrlQuery()
    url_query.page_uri = canonical_uri(artifact.page_uri)
    url_query.generation_id = generation.id
    url_query.artifact_idx = artifact_idx
    url_query.save()

    return url_query

def save_generation(generation):
    assert generation.id == -1

    ttransport = TTransport.TMemoryBuffer()
    tprotocol = TBinaryProtocol.TBinaryProtocol(ttransport)
    generation.write(tprotocol)
    generation_ser = ttransport.getvalue()

    generation_store = GenerationStore()
    generation_store.generation_ser = generation_ser
    generation_store.save()

    generation.id = generation_store.id

    return generation

def load_generation(id):
    try:
        generation_store = GenerationStore.objects.get(id=id)
    except GenerationStore.DoesNotExist as e:
        raise Error(str(e))

    ttransport = TTransport.TMemoryBuffer(generation_store.generation_ser)
    tprotocol = TBinaryProtocol.TBinaryProtocol(ttransport)
    generation = model.Generation()
    generation.read(tprotocol)
    generation.id = generation_store.id

    return generation


class Generation(models.Model):
    IN_PROGRESS = 1
    CLOSED = 2
    STATUS_CHOICES = (
        (IN_PROGRESS, 'inprogress'),
        (CLOSED, 'closed'))

    status = models.IntegerField(default=IN_PROGRESS, choices=STATUS_CHOICES)
    time_added = models.DateTimeField()
    time_closed = models.DateTimeField(null=True)

    @staticmethod
    def add(right_now):
        assert isinstance(right_now, datetime.datetime)

        generation = Generation()
        generation.status = Generation.IN_PROGRESS
        generation.time_added = right_now
        generation.save()

        return generation

    @staticmethod
    def latest():
        return Generation.objects.all().filter(status=Generation.CLOSED).order_by('-id').first()

    @staticmethod
    def next(from_id):
        # If somebody asks for the next iteration after the one with id=1, we return the first one,
        # non-the-less. Simpler to write things this way, rather than in the query. Also, we assume
        # the first generation is always open.
        next_id = max(from_id, 2)
        try:
            return Generation \
                .objects \
                .all() \
                .filter(id__lt=next_id, status=Generation.CLOSED) \
                .order_by('-id') \
                .first()
        except Generation.DoesNotExist as e:
            return Generation.latest()

    def close(self, right_now):
        assert isinstance(right_now, datetime.datetime)

        self.status = Generation.CLOSED
        self.time_closed = right_now

    def to_json_dict(self):
        json_dict = {}

        json_dict['id'] = str(self.id)
        json_dict['status'] = Generation.STATUS_CHOICES[self.status-1][1]
        json_dict['time_added'] = self.time_added.strftime(defines.TIME_FORMAT)
        json_dict['time_closed'] = self.time_closed.strftime(defines.TIME_FORMAT)
        # json_dict['screen_configs'] = defines.IMAGE_SAVE_SCREEN_CONFIGS.copy()
        # json_dict['screen_configs'].update(defines.VIDEO_SAVE_SCREEN_CONFIGS)
        json_dict['artifact_sources'] = {}
        json_dict['artifacts'] = []

        for artifact in self.artifacts.all():
            if str(artifact.artifact_source.id) not in json_dict['artifact_sources']:
                json_dict['artifact_sources'][str(artifact.artifact_source.id)] = artifact.artifact_source.to_json_dict()
            json_dict['artifacts'].append(artifact.to_json_dict())

        return json_dict


class ArtifactSource(models.Model):
    start_page_url = models.URLField(db_index=True, unique=True)
    name = models.CharField(max_length=64)
    time_added = models.DateTimeField()

    @staticmethod
    def all():
        return ArtifactSource.objects.all().order_by('id')

    @staticmethod
    def add(start_page_url, name, right_now):
        assert isinstance(start_page_url, basestring)
        assert isinstance(name, basestring)
        assert isinstance(right_now, datetime.datetime)

        try:
            artifact_source = ArtifactSource.objects.get(start_page_url=start_page_url)
            raise Error('Online shop with start page URL "%s" already exists' % start_page_url)
        except ArtifactSource.DoesNotExist as e:
            pass

        artifact_source = ArtifactSource()
        artifact_source.start_page_url = start_page_url
        artifact_source.name = name
        artifact_source.time_added = right_now
        artifact_source.save()

        return artifact_source

    def to_json_dict(self):
        json_dict = {}

        json_dict['id'] = str(self.id)
        json_dict['start_page_url'] = self.start_page_url
        json_dict['name'] = self.name
        json_dict['time_added'] = self.time_added.strftime(defines.TIME_FORMAT)

        return json_dict


class Artifact(models.Model):
    page_url = models.URLField(db_index=True, unique=True)
    generation = models.ForeignKey(Generation, related_name='artifacts')
    artifact_source = models.ForeignKey(ArtifactSource, related_name='artifacts')
    title = models.CharField(max_length=100)
    images_description_coded = models.TextField()

    # Schema for the image_description:
    # images_description :: List ImageDescription
    # type ImageDescription
    #     subtitle String
    #     description String
    #     source_uri URI  # URI of the source
    #     original_image_uri_path URI # URI of the system stored source image
    #     image_data Map ScreenConfig ImageData
    # type ImageData
    #   ImageSet
    #     full_image_desc TileData # information for the full image.
    #     tiles_desc List TileData # List of information for each tile
    #   AnimationSet
    #     time_between_frames Double
    #     frames_desc List TileData # List of information for each tile
    #   TooLarge
    # type TileData
    #     width UInt32
    #     height UInt32
    #     uri_path Uri

    @staticmethod
    def all():
        return Artifact.objects.all().order_by('id')

    @staticmethod
    def add(page_url, generation, artifact_source, title, images_description_coded):
        assert isinstance(page_url, basestring)
        assert isinstance(generation, Generation)
        assert isinstance(artifact_source, ArtifactSource)
        assert isinstance(title, basestring)
        assert isinstance(images_description_coded, basestring)

        try:
            artifact = Artifact.objects.get(page_url=page_url)
            raise Error('Artifact with page URL "%s" arleady exists' % page_url)
        except Artifact.DoesNotExist as e:
            pass

        artifact = Artifact()
        artifact.page_url = page_url
        artifact.generation = generation
        artifact.artifact_source = artifact_source
        artifact.title = title
        # artifact.images_description = images_description
        artifact.images_description_coded = images_description_coded
        artifact.save()

        return artifact

    @staticmethod
    def get_by_page_url(artifact_page_url):
        assert isinstance(artifact_page_url, basestring)

        try:
           artifact = Artifact.objects.get(page_url=artifact_page_url)
        except Artifact.DoesNotExist as e:
            raise Error('Artifact "%s" does not exist' % artifact_page_url)

        return artifact

    # def __init__(self, *args, **kwargs):
    #     super(Artifact, self).__init__(*args, **kwargs)
    #     if self.images_description_coded:
    #         self.images_description = json.loads(self.images_description_coded)

    def to_json_dict(self):
        json_dict = {}

        json_dict['id'] = str(self.id)
        json_dict['page_url'] = self.page_url
        json_dict['artifact_source_id'] = str(self.artifact_source.id)
        json_dict['title'] = self.title
        # json_dict['images_description'] = self.images_description

        return json_dict
