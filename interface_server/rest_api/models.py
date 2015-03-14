import datetime
import json

from django.db import models

import common.defines as defines
import common.flow_annotation as flow_annotation


class Error(Exception):
    """Error raised by model objects."""
    pass

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
        next_id = from_id - 1
        try:
            return Generation.objects.get(id=next_id)
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
    @flow_annotation.crawl_artifact_sources
    def all():
        return ArtifactSource.objects.all().order_by('id')

    @staticmethod
    @flow_annotation.populate_database
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
    #     uri_paths List Uri # List of URI into which the image was broken down.
    #                        # Images which are too tall are broken down like into
    #                        # multiple frames.
    #   AnimationSet
    #     uri_paths List Uri # GIFs and other animations are broken down as individual
    #                        # frames and stored as such, for easier client display.
    #     time_between_frames Double

    @staticmethod
    def all():
        return Artifact.objects.all().order_by('id')

    @staticmethod
    def add(page_url, generation, artifact_source, title, images_description):
        assert isinstance(page_url, basestring)
        assert isinstance(generation, Generation)
        assert isinstance(artifact_source, ArtifactSource)
        assert isinstance(title, basestring)
        assert isinstance(images_description, list)
        assert all(isinstance(d, dict) for d in images_description)
        assert all('subtitle' in d for d in images_description)
        assert all(isinstance(d['subtitle'], basestring) for d in images_description)
        assert all('description' in d for d in images_description)
        assert all(isinstance(d['description'], basestring) for d in images_description)
        assert all('url_path' for d in images_description)
        assert all(isinstance(d['url_path'], basestring) for d in images_description)

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
        artifact.images_description = images_description
        artifact.images_description_coded = json.dumps(images_description)
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

    def __init__(self, *args, **kwargs):
        super(Artifact, self).__init__(*args, **kwargs)
        if self.images_description_coded:
            self.images_description = json.loads(self.images_description_coded)

    def to_json_dict(self):
        json_dict = {}

        json_dict['id'] = str(self.id)
        json_dict['page_url'] = self.page_url
        json_dict['artifact_source_id'] = str(self.artifact_source.id)
        json_dict['title'] = self.title
        json_dict['images_description'] = self.images_description

        return json_dict
