package org.peaceful.banana.git

import org.peaceful.banana.GitSyncer
import org.peaceful.banana.User
import org.scribe.model.Token
import uk.co.desirableobjects.oauth.scribe.OauthService
import org.peaceful.banana.gitdata.Repository
import grails.converters.JSON

/**
 This file is part of Peaceful Banana.

 Peaceful Banana is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 Peaceful Banana is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with Peaceful Banana.  If not, see <http://www.gnu.org/licenses/>.
 */
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
