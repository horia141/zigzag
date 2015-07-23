from django.shortcuts import redirect
import user_agents

import common.defines.constants as defines


def shareapp(request):
    user_agent_string = request.META['HTTP_USER_AGENT']
    user_agent = user_agents.parse(user_agent_string)

    if user_agent.is_mobile:
        if user_agent.device.family == 'Android':
            return redirect(defines.URLS_GOOGLE_PLAY_STORE)
        elif user_agent.device.family == 'iPhone':
            return redirect(defines.URLS_APPSTORE)
        else:
            return redirect(defines.URLS_MAIN)
    else:
        return redirect(defines.URLS_MAIN)
