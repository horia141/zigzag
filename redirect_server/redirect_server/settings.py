# SECURITY WARNING: keep the secret key used in production secret!
SECRET_KEY = '3(g3o(71@@d3^tg^8=ean^ja*7)p@9$nox+08r)m$oru4&#(6-'

# SECURITY WARNING: don't run with debug turned on in production!
DEBUG = <%= node.default['application']['runtime']['debug'] %>

TEMPLATE_DEBUG = <%= node.default['application']['runtime']['debug'] %>

ALLOWED_HOSTS = []

INSTALLED_APPS = (
    'shareapp',
)

MIDDLEWARE_CLASSES = (
)

ROOT_URLCONF = 'redirect_server.urls'

WSGI_APPLICATION = 'redirect_server.wsgi.application'

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
