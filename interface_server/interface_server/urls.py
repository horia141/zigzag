from django.conf.urls import patterns, include, url

urlpatterns = patterns('',
    url(r'^api/v1/', include('rest_api.urls')),
)
