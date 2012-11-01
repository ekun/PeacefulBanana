package org.peaceful.banana

import uk.co.desirableobjects.oauth.scribe.OauthService
import org.eclipse.egit.github.core.service.UserService
import org.eclipse.egit.github.core.client.GitHubClient
import org.scribe.model.Token
import org.eclipse.egit.github.core.User
import org.eclipse.egit.github.core.service.RepositoryService

class SettingsController {

    static allowedMethods = [changeSelectedRepo: 'POST']

    def springSecurityService

    def index() {
    }

    def github() {
        def OauthService oauthService = new OauthService() //would work if you're not in a spring-managed class.
        Token gitToken = (Token)session[oauthService.findSessionKeyForAccessToken('github')]

        def user = org.peaceful.banana.User.get(springSecurityService.principal.id)

        def User gitUser = null

        if (gitToken != null) {
            def UserService gitOAuthService = new UserService(
                    new GitHubClient().setOAuth2Token(gitToken.token))
            def RepositoryService repositoryService = new RepositoryService(gitOAuthService.getClient())
            if (gitOAuthService.getUser() != null) {
                gitUser = gitOAuthService.getUser()
            }

            [user: user, gitUser: gitUser, repositories: repositoryService.getRepositories()]
        }

    }

    def changeSelectedRepo() {
        def user = org.peaceful.banana.User.get(springSecurityService.principal.id)
        user?.selectedRepo = params.getInt("repoSelection")

        redirect(action: 'github')
    }
}
