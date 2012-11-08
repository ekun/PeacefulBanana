package org.peaceful.banana.git

import org.peaceful.banana.User
import org.scribe.model.Token
import uk.co.desirableobjects.oauth.scribe.OauthService
import org.peaceful.banana.gitdata.Repository
import grails.converters.JSON

class GithubSyncController {

    def springSecurityService
    OauthService oauthService

    def index() {
        // Check if there is anything new to fetch
        def user = User.get(springSecurityService.principal.id)
        if(user?.selectedRepo && session[oauthService.findSessionKeyForAccessToken('github')]) {
            GitHubService gitHubService = new GitHubService((Token)session[oauthService.findSessionKeyForAccessToken('github')])
            def table = [update: gitHubService.getRepository(user.selectedRepo).updatedAt.after(Repository.findByGithubId(user.selectedRepo).updated)]
            render table  as JSON
        } else {
            def table = [update: false]
            render table  as JSON
        }
    }

    def sync() {
        def user = User.get(springSecurityService.principal.id)
        // runAsync or callAsync?
        // Depends on how
    }
}
