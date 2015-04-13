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


def serialize_generation(generation):
    ttransport = TTransport.TMemoryBuffer()
    tprotocol = TBinaryProtocol.TBinaryProtocol(ttransport)
    generation.write(tprotocol)
    generation_ser = ttransport.getvalue()

    return generation_ser


def parse_generation(generation_ser):
    ttransport = TTransport.TMemoryBuffer(generation_ser)
    tprotocol = TBinaryProtocol.TBinaryProtocol(ttransport)
    generation = model.Generation()
    generation.read(tprotocol)

    return generation


def save_generation(generation):
    assert generation.id == -1

    generation_store = GenerationStore()
    generation_store.generation_ser = serialize_generation(generation)
    generation_store.save()

    generation.id = generation_store.id

    return generation


def load_generation(id):
    try:
        generation_store = GenerationStore.objects.get(id=id)
    except GenerationStore.DoesNotExist as e:
        raise Error(str(e))

    generation = parse_generation(generation_store.generation_ser)
    generation.id = generation_store.id

    return generation


def load_latest_generation():
    generation_store = GenerationStore.objects.all().order_by('-id').first()
    generation = parse_generation(generation_store.generation_ser)
    generation.id = generation_store.id

    return generation


def load_next_generation(next_id):
    generation_store = GenerationStore.objects.all().filter(id__lt=next_id).order_by('-id').first()

    if generation_store is None:
        return load_latest_generation()

    generation = parse_generation(generation_store.generation_ser)
    generation.id = generation_store.id

    return generation
