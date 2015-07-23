"""Mapping from paths to view functions."""

from django.conf.urls import patterns, include, url

import shareapp.views

urlpatterns = patterns('',
    url(r'^$', shareapp.views.shareapp)
)
