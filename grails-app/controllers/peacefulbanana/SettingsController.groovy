package peacefulbanana

import uk.co.desirableobjects.oauth.scribe.OauthService

class SettingsController {

    def index() {
    }

    def github() {

        if (params.asdf != null) {
            def OauthService oauthService = new OauthService() //would work if you're not in a spring-managed class.
            log.error(oauthService.findSessionKeyForAccessToken('github'))
        }
    }
}
