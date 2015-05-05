import datetime
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
    output = request.GET.get('output', '')

    if from_id == 'latest':
        generation = datastore.load_latest_generation()
    else:
        try:
            from_id_nr = int(from_id, 10)
            generation = datastore.load_next_generation(from_id_nr)
        except ValueError as e:
            return HttpResponseBadRequest('Invalid "from" parameter')

    latest_analysis_result = datastore.load_latest_analysis_result()

    if (latest_analysis_result.month.total_bytes >= 
        defines.BANDWIDTH_ALERT_BYTES_PER_MONTH):
        bandwidth_alert = True
    else:
        bandwidth_alert = False

    response = api.NextGenResponse(generation, bandwidth_alert)

    if output == 'thrift':
        response_ser = datastore.serialize(response)
        content_type = 'application/x-thrift'
    elif output == 'json':
        response_ser = datastore.serialize_response_as_json(response)
        content_type = 'application/json'
    else:
        return HttpResponseBadRequest('Invalid "output" parameter')

    return HttpResponse(response_ser, status=200, content_type=content_type)
