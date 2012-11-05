package org.peaceful.banana

import org.eclipse.egit.github.core.Repository
import org.peaceful.banana.git.GitHubService
import org.scribe.model.Token
import uk.co.desirableobjects.oauth.scribe.OauthService
import org.peaceful.banana.git.util.CommitStatistics

class RepositoriesController {
    OauthService oauthService
    def springSecurityService

    def index() {

        def user = User.get(springSecurityService.principal.id)
        if (user.selectedRepo != 0) {

            // if user.access-token for github is not set redirect to /settings/github
            Token githubAccessToken = (Token)session[oauthService.findSessionKeyForAccessToken('github')]
            if (!githubAccessToken) {
                log.debug("Ingen accesstoken satt, redirecter.")
                redirect(controller: 'settings', action: 'github')
            } else {
                def columns = [['string', 'Name'], ['number', 'Impact']]
                //def chartData = [['Even', 11], ['Marius', 2]]
                GitHubService gitHubService = new GitHubService(githubAccessToken)
                Repository repository = gitHubService.getRepository(user.selectedRepo)

   /*           ArrayList<Object[]> impactPerUser = new ArrayList()
                for (CommitStatistics statistics : gitHubService.getRepositoryImpact(repository)) {
                    Object[] array = new Object[2]
                    array[0] = statistics.getUser()
                    array[1] = statistics.getImpact()
                    impactPerUser.add(array)
                } */

                [selectedRepo: repository,
                        columns: columns, chartData: gitHubService.getRepositoryCommitStats(repository)
                ]
            }
        }
    }

    def milestone() {
        def user = User.get(springSecurityService.principal.id)
        if (user.selectedRepo != 0) {
            // if user.access-token for github is not set redirect to /settings/github
            Token githubAccessToken = (Token)session[oauthService.findSessionKeyForAccessToken('github')]
            if (!githubAccessToken) {
                log.debug("Ingen accesstoken satt, redirecter.")
                redirect(controller: 'settings', action: 'github')
            } else {
                GitHubService gitHubService = new GitHubService(githubAccessToken)
                Repository repository = gitHubService.getRepository(user.selectedRepo)

                [openMilestones: gitHubService.getMilestones(repository, true),
                    closedMilestones: gitHubService.getMilestones(repository, false)
                ]
            }
        }
    }


    def issue() {

    }

    def tagcloud() {

    }

    def statistics() {

    }
}