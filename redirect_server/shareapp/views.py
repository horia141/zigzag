from django.shortcuts import redirect

import common.defines.constants as defines


def shareapp(request):
    return redirect(defines.URLS_MAIN)
