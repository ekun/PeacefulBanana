package org.peaceful.banana

import org.peaceful.banana.git.GitHubService
import org.peaceful.banana.git.GithubSyncController
import org.scribe.model.Token
import uk.co.desirableobjects.oauth.scribe.OauthService

class SettingsController {

    static allowedMethods = [changeSelectedRepo: 'POST']

    def springSecurityService
    GitHubService gitHubService
    OauthService oauthService

    def index() {
        Token gitToken = (Token)session[oauthService.findSessionKeyForAccessToken('github')]

        def user = User.get(springSecurityService.principal.id)

        if (gitToken != null) {
            gitHubService = new GitHubService(gitToken)

            [user: user, gitUser: gitHubService.getAuthenticatedUser()]
        }
    }

    def github() {
        def user = User.get(springSecurityService.principal.id)
        Token gitToken = (Token)session[oauthService.findSessionKeyForAccessToken('github')]

        if (gitToken != null) {

            gitHubService = new GitHubService(gitToken)

            [user: user, gitUser: gitHubService.getAuthenticatedUser(), repositories: gitHubService.getRepositories()]
        }
    }
    /**
     * method to changeSelectedRepo in settings
     * this could maybe be moved into githubcontroller, but it is settings specific so it might aswell be here =)
     * @return
     */

    def changeSelectedRepo() {
        def user = User.get(springSecurityService.principal.id)
        user?.selectedRepo = params.getLong("repoSelection")
        user.save()

        new GithubSyncController().sync()

        render("Completed syncing data from github.")
    }
}
