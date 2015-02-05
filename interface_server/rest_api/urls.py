"""Mapping from paths to view functions."""

from django.conf.urls import patterns, include, url

import rest_api.views

urlpatterns = patterns('',
    url(r'^nextgen$', rest_api.views.nextgen)
)
