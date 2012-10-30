package peacefulbanana

import uk.co.desirableobjects.oauth.scribe.OauthService
import org.eclipse.egit.github.core.service.UserService
import org.eclipse.egit.github.core.client.GitHubClient
import org.scribe.model.Token
import org.eclipse.egit.github.core.User

class SettingsController {

    def index() {
    }

    def github() {
        def OauthService oauthService = new OauthService() //would work if you're not in a spring-managed class.
        Token gitToken = session[oauthService.findSessionKeyForAccessToken('github')]

        def User gitUser = null

        if (gitToken != null) {
            def UserService gitOAuthService = new UserService(
                    new GitHubClient().setOAuth2Token(gitToken.token))

            if (gitOAuthService.getUser() != null) {
                gitUser = gitOAuthService.getUser()
            }

            [gitUser: gitUser]
        }

    }
}
