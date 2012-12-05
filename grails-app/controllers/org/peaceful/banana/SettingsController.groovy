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

            user.gitLogin = gitHubService.getAuthenticatedUser().login
            user.save()

            [user: user, gitUser: gitHubService.getAuthenticatedUser(), repositories: gitHubService.getRepositories()]
        }
    }

    def changeSelectedRepo() {
        def user = User.get(springSecurityService.principal.id)
        def today = new Date().clearTime() //Today from 00:00:00
        if(user?.selectedRepo == 0){
            new Notification(user: user, title: "Congratulations", body: "You have selected your first project!", notificationType: NotificationType.OTHER).save(flush: true)
            new Notification(user: user, title: "Daily Reflection", body: "You have not completed your daily reflection! Click here to do this now", notificationType: NotificationType.REFLECTION).save(flush: true)
        }
        else {
            def latestNotification = user?.getLatestReflectionNotification()?.get(0)?.dateCreated //Improve this

            // Create a new reflection notification if one haven't been made today.
            if(latestNotification != null && latestNotification.before(today))  {
                new Notification(user: user, title: "Daily reflection", body: "You have not completed your daily reflection! Click here to do this now", notificationType: NotificationType.REFLECTION).save(flush: true)
                //Clicking notification should lead to summary form
            }
        }
        user?.selectedRepo = params.getLong("repoSelection")
        user.save()

        new GithubSyncController().sync()

        render("Completed syncing data from github.")
    }
}
