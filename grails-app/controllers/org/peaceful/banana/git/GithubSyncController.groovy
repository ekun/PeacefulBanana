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

                // update the users data.
                def gitUser = gitHubService.getAuthenticatedUser()
                def nameUser = gitUser.name.split(" ")
                user.firstName = ""

                for (int i = 0; i < nameUser.length -1; i ++) {
                    user.firstName += nameUser[i].capitalize() + " "
                }
                user.firstName = user.firstName.substring(0, user.firstName.length() - 1);

                user.lastName = nameUser[nameUser.length-1] // Lastname
                user.lastName.capitalize()

                if (!user.gitLogin)
                    user.gitLogin = gitUser.login

                user.save()

                // Check and indicate that a new commit has occurred.
                session["syncAvail"] = gitHubService.getRepository(user.selectedRepo).updatedAt.after(Repository.findByGithubId(user.selectedRepo).updated)
                session["syncAvailTimestamp"] = Repository.findByGithubId(user.selectedRepo).updated

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

        def repo = Repository.findByGithubId(user.selectedRepo)

        session["lastCheck"] = System.currentTimeMillis()

        if (repo.updated == session["syncAvailTimestamp"]) {
            def gitSync = new GitSyncer()
            gitSync.sync(user, (Token)session[oauthService.findSessionKeyForAccessToken('github')])
        }

        // Sync is now complete and indicator is switched.
        session["syncAvail"] = false
        session["syncAvailTimestamp"] = null

        def table = [update: true]
        render table as JSON
    }
}
