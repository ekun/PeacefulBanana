package org.peaceful.banana

import org.eclipse.egit.github.core.service.UserService
import org.scribe.model.Token
import org.eclipse.egit.github.core.User
import org.eclipse.egit.github.core.service.RepositoryService
import org.peaceful.banana.git.GitHubService
import org.peaceful.banana.gitdata.Commit
import org.peaceful.banana.json.GitDataController
import org.peaceful.banana.git.GithubSyncController
import uk.co.desirableobjects.oauth.scribe.OauthService
import grails.plugin.springsecurity.oauth.OAuthToken

class SettingsController {

    static allowedMethods = [changeSelectedRepo: 'POST']

    def GithubController githubController = new GithubController()
    GitHubService gitHubService
    OauthService oauthService

    def index() {
        Token gitToken = githubController.getToken()

        def user = githubController.getPrincipal()
        def User gitUser = null

        if (gitToken != null) {
            def UserService gitOAuthService = githubController.setOAuthToken(gitToken.token)
            gitHubService = new GitHubService(gitToken)

            if (gitOAuthService.getUser() != null) {
                gitUser = gitOAuthService.getUser()
            }
            [user: user, gitUser: gitUser]
        }
    }

    def github() {
        def user = githubController.getPrincipal()
        Token gitToken = githubController.getToken()
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

        new GithubSyncController().sync()

        render("Completed syncing data from github.")
    }
}
