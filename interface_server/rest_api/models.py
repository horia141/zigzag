import datetime
import json
import pytz
import urlparse

from django.db import models
from thrift.protocol import TBinaryProtocol
from thrift.transport import TTransport

import common.defines.constants as defines
import common.model.ttypes as model
import log_analyzer.protos.ttypes as log_analyzer_protos


class Error(Exception):
    pass


class GenerationStore(models.Model):
    generation_ser = models.BinaryField()


class ArtifactUrlQuery(models.Model):
    page_uri = models.URLField(primary_key=True, db_index=True, unique=True)
    generation_id = models.IntegerField()
    artifact_idx = models.IntegerField()


class LogAnalyzerAnalysisResultStore(models.Model):
    analysis_result_ser = models.BinaryField()


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


def serialize_response_as_json(next_gen_response):
    def do_generation(generation):
        generation_json = {}

        generation_json['id'] = generation.id
        generation_json['datetime_started_ts'] = generation.datetime_started_ts
        generation_json['datetime_started'] = datetime.datetime\
            .fromtimestamp(generation.datetime_started_ts, pytz.utc)\
            .strftime(defines.TIME_FORMAT)
        generation_json['datetime_ended_ts'] = generation.datetime_ended_ts
        generation_json['datetime_ended'] = datetime.datetime\
            .fromtimestamp(generation.datetime_ended_ts, pytz.utc)\
            .strftime(defines.TIME_FORMAT)
        generation_json['artifact_sources'] = {}
        generation_json['image_screen_config'] = do_screen_config(defines.IMAGE_SCREEN_CONFIG)
        generation_json['image_video_config'] = do_screen_config(defines.VIDEO_SCREEN_CONFIG)
        generation_json['artifacts'] = []

        for id, artifact_source in generation.artifact_sources.iteritems():
            generation_json['artifact_sources'][id] = do_artifact_source(artifact_source)

        for artifact in generation.artifacts:
            generation_json['artifacts'].append(do_artifact(artifact))

        return generation_json

    def do_artifact_source(artifact_source):
        artifact_source_json = {}

        artifact_source_json['id'] = artifact_source.id
        artifact_source_json['name'] = artifact_source.name
        artifact_source_json['artifact_title_name'] = artifact_source.artifact_title_name
        artifact_source_json['start_page_uri'] = artifact_source.start_page_uri
        if artifact_source.subdomains is not None:
            artifact_source_json['subdomains'] = list(artifact_source.subdomains)
        else:
            artifact_source_json['subdomains'] = []

        return artifact_source_json

    def do_screen_config(screen_config):
        screen_config_json = {}

        screen_config_json['name'] = screen_config.name
        screen_config_json['width'] = screen_config.width

        return screen_config_json

    def do_artifact(artifact):
        artifact_json = {}

        # TODO(horia141): use a proper id for the artifacts, or at least an externally stable hash.
        artifact_json['id'] = hash(artifact.page_uri)
        artifact_json['page_uri'] = artifact.page_uri
        artifact_json['title'] = artifact.title
        artifact_json['artifact_source_pk'] = artifact.artifact_source_pk
        artifact_json['photo_descriptions'] = []

        for photo_description in artifact.photo_descriptions:
            artifact_json['photo_descriptions'].append(do_photo_description(photo_description))

        return artifact_json

    def do_photo_description(photo_description):
        photo_description_json = {}

        photo_description_json['subtitle'] = photo_description.subtitle
        photo_description_json['description'] = photo_description.description
        photo_description_json['source_uri'] = photo_description.source_uri
        photo_description_json['photo_data'] = do_photo_data(photo_description.photo_data)

        return photo_description_json

    def do_photo_data(photo_data):
        photo_data_json = {}

        if photo_data.too_big_photo_data is not None:
            photo_data_json['type'] = 'too-big-photo-data'
            photo_data_json['too_big_photo_data'] = do_too_big_photo_data(photo_data.too_big_photo_data)
        elif photo_data.image_photo_data is not None:
            photo_data_json['type'] = 'image-photo-data'
            photo_data_json['image_photo_data'] = do_image_photo_data(photo_data.image_photo_data)
        elif photo_data.video_photo_data is not None:
            photo_data_json['type'] = 'video-photo-data'
            photo_data_json['video_photo_data'] = do_video_photo_data(photo_data.video_photo_data)
        else:
            raise Error('Invalid photo data')

        return photo_data_json

    def do_too_big_photo_data(too_big_photo_data):
        too_big_photo_data_json = {}

        return too_big_photo_data_json

    def do_image_photo_data(image_photo_data):
        image_photo_data_json = {}

        image_photo_data_json['tiles'] = []

        for tile in image_photo_data.tiles:
            image_photo_data_json['tiles'].append(do_tile_data(tile))

        return image_photo_data_json

    def do_video_photo_data(video_photo_data):
        video_photo_data_json = {}

        video_photo_data_json['first_frame'] = do_tile_data(video_photo_data.first_frame)
        video_photo_data_json['video'] = do_tile_data(video_photo_data.video)
        video_photo_data_json['frames_per_sec'] = video_photo_data.frames_per_sec
        video_photo_data_json['time_between_frames_ms'] = video_photo_data.time_between_frames_ms

        return video_photo_data_json

    def do_tile_data(tile_data):
        tile_data_json = {}

        tile_data_json['width'] = tile_data.width
        tile_data_json['height'] = tile_data.height
        tile_data_json['uri_path'] = tile_data.uri_path

        return tile_data_json
        
    response_json = {}

    response_json['generation'] = do_generation(next_gen_response.generation)
    response_json['bandwidth_alert'] = next_gen_response.bandwidth_alert

    response_ser = json.dumps(response_json)

    return response_ser


def serialize(thrift_object):
    ttransport = TTransport.TMemoryBuffer()
    tprotocol = TBinaryProtocol.TBinaryProtocol(ttransport)
    thrift_object.write(tprotocol)
    thrift_object_ser = ttransport.getvalue()

    return thrift_object_ser


def parse(constructor, thrift_object_ser):
    ttransport = TTransport.TMemoryBuffer(thrift_object_ser)
    tprotocol = TBinaryProtocol.TBinaryProtocol(ttransport)
    thrift_object_ser = constructor()
    thrift_object_ser.read(tprotocol)

    return thrift_object_ser


def save_generation(generation):
    assert generation.id == -1

    generation_store = GenerationStore()
    generation_store.generation_ser = serialize(generation)
    generation_store.save()

    generation.id = generation_store.id

    return generation


def load_generation(id):
    try:
        generation_store = GenerationStore.objects.get(id=id)
    except GenerationStore.DoesNotExist as e:
        raise Error(str(e))

    generation = parse(model.Generation, generation_store.generation_ser)
    generation.id = generation_store.id

    return generation


def load_latest_generation():
    generation_store = GenerationStore.objects.all().order_by('-id').first()
    generation = parse(model.Generation, generation_store.generation_ser)
    generation.id = generation_store.id

    return generation


def load_next_generation(next_id):
    generation_store = GenerationStore.objects.all().filter(id__lt=next_id).order_by('-id').first()

    if generation_store is None:
        return load_latest_generation()

    generation = parse(model.Generation, generation_store.generation_ser)
    generation.id = generation_store.id

    return generation


def save_analysis_result(analysis_result):
    assert analysis_result.id == -1

    analysis_result_store = LogAnalyzerAnalysisResultStore()
    analysis_result_store.analysis_result_ser = serialize(analysis_result)
    analysis_result_store.save()

    analysis_result.id = analysis_result_store.id

    return analysis_result


def load_latest_analysis_result():
    analysis_result_store = LogAnalyzerAnalysisResultStore.objects.all().order_by('-id').first()
    analysis_result = parse(log_analyzer_protos.AnalysisResult, analysis_result_store.analysis_result_ser)
    analysis_result.id = analysis_result_store.id

    return analysis_result
