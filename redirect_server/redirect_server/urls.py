from django.conf.urls import include, url

urlpatterns = [
    url(r'^getpicjar', include('shareapp.urls')),
]
