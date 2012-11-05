package org.peaceful.banana

import uk.co.desirableobjects.oauth.scribe.OauthService
import org.eclipse.egit.github.core.service.UserService
import org.eclipse.egit.github.core.client.GitHubClient
import org.scribe.model.Token
import org.eclipse.egit.github.core.User
import org.eclipse.egit.github.core.service.RepositoryService

class SettingsController {

    static allowedMethods = [changeSelectedRepo: 'POST']

    def GithubController githubController = new GithubController()

    def index() {
    }

    def github() {
        Token gitToken = githubController.getToken()
        def user = githubController.getPrincipalID()
        def User gitUser = null

        if (gitToken != null) {
            def UserService gitOAuthService = githubController.setOAuthToken(gitToken.token)
            def RepositoryService repositoryService = new RepositoryService(gitOAuthService.getClient())
            if (gitOAuthService.getUser() != null) {
                gitUser = gitOAuthService.getUser()
            }
            [user: user, gitUser: gitUser, repositories: repositoryService.getRepositories()]
        }
    }
    /**
     * method to changeSelectedRepo in settings
     * this could maybe be moved into githubcontroller, but it is settings specific so it might aswell be here =)
     * @return
     */

    def changeSelectedRepo() {
        def user = githubController.getPrincipal()
        user?.selectedRepo = params.getLong("repoSelection")
        user.save()

        redirect(action: 'github')
    }
}
