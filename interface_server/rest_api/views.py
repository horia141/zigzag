import datetime
import json
import pytz

from django.http import HttpResponse
from django.http import HttpResponseBadRequest
from django.http import HttpResponseNotAllowed

import common.defines.constants as defines
import common.api.ttypes as api
import rest_api.models as datastore


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

    response = api.NextGenResponse(generation, bandwidth_alert=False)        
    response_ser = datastore.serialize_response(response)
    return HttpResponse(response_ser, status=200, content_type='application/x-thrift')
