package org.peaceful.banana.git

import org.peaceful.banana.GitSyncer
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
        if (!session["lastCheck"]){
            // if not set this will set it!
            session["lastCheck"] = System.currentTimeMillis()-60001;
        }

        if (springSecurityService.principal.class != String) {
            def user = User.get(springSecurityService.principal.id)

            if(user?.selectedRepo && session[oauthService.findSessionKeyForAccessToken('github')]
                    && new Date((long)session["lastCheck"]).before(new Date(System.currentTimeMillis()-60000))) {
                session["lastCheck"] = System.currentTimeMillis()

                GitHubService gitHubService = new GitHubService((Token)session[oauthService.findSessionKeyForAccessToken('github')])

                // Indicating that a sync is availible
                session["syncAvail"] = gitHubService.getRepository(user.selectedRepo).updatedAt.after(Repository.findByGithubId(user.selectedRepo).updated)

                def table = [update: session["syncAvail"]]

                render table  as JSON
            } else {
                def table = [update: session["syncAvail"]]
                render table as JSON
            }
        } else {
            def table = [update: false]
            render table as JSON
        }
    }

    def sync() {
        def user = User.get(springSecurityService.principal.id)

        session["lastCheck"] = System.currentTimeMillis()

        def gitSync = new GitSyncer()
        gitSync.sync(user, (Token)session[oauthService.findSessionKeyForAccessToken('github')])

        // Sync is now complete and indicator is switched.
        session["syncAvail"] = false

        def table = [update: true]
        render table as JSON
    }
}
