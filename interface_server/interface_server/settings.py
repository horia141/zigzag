"""
Django settings for interface_server project.

For more information on this file, see
https://docs.djangoproject.com/en/1.6/topics/settings/

For the full list of settings and their values, see
https://docs.djangoproject.com/en/1.6/ref/settings/
"""

# Quick-start development settings - unsuitable for production
# See https://docs.djangoproject.com/en/1.6/howto/deployment/checklist/

# SECURITY WARNING: keep the secret key used in production secret!
SECRET_KEY = '43_@^r!wysh7l35dndt*=$x!fdg9kh#&a-1=hp(()&aar#dd_!'

# SECURITY WARNING: don't run with debug turned on in production!
DEBUG = True

TEMPLATE_DEBUG = True

ALLOWED_HOSTS = []


# Application definition

INSTALLED_APPS = (
    'rest_api',
)

MIDDLEWARE_CLASSES = (
)

ROOT_URLCONF = 'interface_server.urls'

WSGI_APPLICATION = 'interface_server.wsgi.application'


# Database
# https://docs.djangoproject.com/en/1.6/ref/settings/#databases

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

# Internationalization
# https://docs.djangoproject.com/en/1.6/topics/i18n/

LANGUAGE_CODE = 'en-us'

TIME_ZONE = 'UTC'

USE_I18N = True

USE_L10N = True

USE_TZ = True


# Static files (CSS, JavaScript, Images)
# https://docs.djangoproject.com/en/1.6/howto/static-files/

STATIC_URL = '/static/'
