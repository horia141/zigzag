# SECURITY WARNING: keep the secret key used in production secret!
SECRET_KEY = '43_@^r!wysh7l35dndt*=$x!fdg9kh#&a-1=hp(()&aar#dd_!'

# SECURITY WARNING: don't run with debug turned on in production!
DEBUG = <%= node.default['application']['runtime']['debug'] %>

TEMPLATE_DEBUG = <%= node.default['application']['runtime']['debug'] %>

ALLOWED_HOSTS = []

INSTALLED_APPS = (
    'rest_api',
)

MIDDLEWARE_CLASSES = (
)

ROOT_URLCONF = 'interface_server.urls'

WSGI_APPLICATION = 'interface_server.wsgi.application'


DATABASES = {
    'default': {
        'ENGINE': 'django.db.backends.postgresql_psycopg2',
        'NAME': '<%= node.default['application']['database_name'] %>',
        'USER': '<%= node.default['application']['database']['user'] %>',
        'PASSWORD': '',
        'HOST': '<%= node.default['application']['database']['host'] %>',
        'PORT': '<%= node.default['application']['database']['port'] %>'
    }
}

LANGUAGE_CODE = 'en-us'

TIME_ZONE = 'UTC'

USE_I18N = True

USE_L10N = True

USE_TZ = True

STATIC_URL = '/static/'
