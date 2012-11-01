package org.peaceful.banana

import uk.co.desirableobjects.oauth.scribe.OauthService
import org.scribe.model.Token
import org.eclipse.egit.github.core.service.UserService
import org.eclipse.egit.github.core.client.GitHubClient

/**
 * This class is for dealing with the different github calls
 */
class GithubController {

    static allowedMethods = [changeSelectedRepo: 'POST']
    def springSecurityService
    def OauthService oauthService = new OauthService()

    def index() { }
    /**
     * returns the github access token
     */
    def getToken() {
        Token gitToken = (Token)session[oauthService.findSessionKeyForAccessToken('github')]
        return gitToken
    }

    def setOAuthToken(String token) {
        def UserService gitOAuthService = new UserService(
                new GitHubClient().setOAuth2Token(token))
        return gitOAuthService
    }

    /**
     * returns the authenticated GrailsUser ID(principal ID)
     */
    def getPrincipalID() {
        def user = org.peaceful.banana.User.get(springSecurityService.principal.id)
        return user
    }
}
