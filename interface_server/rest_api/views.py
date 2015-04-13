import datetime
import json
import pytz

from django.http import HttpResponse
from django.http import HttpResponseBadRequest
from django.http import HttpResponseNotAllowed
from django.shortcuts import render

import common.defines as defines
import rest_api.models as datastore

# Create your views here.

class JsonResponse(HttpResponse):
    """A view response object which encodes a JSON object.

    The JSON object is given as an ordinary Python dictionary, and is encoded to a string
    and passed to the standard HttpResponse, which is the super-class.
    """

    def __init__(self, entity_or_list_of_entities, status=200):
        """Construct a JSON response object.

        Regarding the input, the best we can say is "it's kind of complicated". If
        entity_or_list_of_entities is a string, a dict or a list of dicts, it it directly passed to 
        the JSON encoding routine. If it is a model entity or a list of model entities, then the
        corresponding to_json_dict() method is called, which produces a dict.

        Params:
          entity_or_list_of_entities: as described above.
          status: the status code for the response.
        """

        if isinstance(entity_or_list_of_entities, basestring):
            content = entity_or_list_of_entities
        elif isinstance(entity_or_list_of_entities, dict):
            content = entity_or_list_of_entities
        elif isinstance(entity_or_list_of_entities, list):
            if len(entity_or_list_of_entities) > 0 and \
               isinstance(entity_or_list_of_entities[0], dict):
                content = entity_or_list_of_entities
            else:
                content = [e.to_json_dict() for e in entity_or_list_of_entities]
        else:
            content = entity_or_list_of_entities.to_json_dict()

        super(JsonResponse, self).__init__(json.dumps(content), status=status,
            content_type='application/json')


def nextgen(request):
    if request.method != 'GET':
        return HttpResponseNotAllowed(['GET'])

    from_id = request.GET.get('from', '')

    if from_id == 'latest':
        generation = datastore.load_latest_generation()
    else:
        try:
            from_id_nr = int(from_id, 10)
            generation = datastore.load_next_generation(from_id_nr)
        except ValueError as e:
            return HttpResponseBadRequest('Invalid "from" parameter')

    generation_ser = datastore.serialize_generation(generation)
    return HttpResponse(generation_ser, status=200, content_type='application/x-thrift')
