package org.peaceful.banana

import org.eclipse.egit.github.core.Repository
import org.peaceful.banana.git.GitHubService
import org.scribe.model.Token
import uk.co.desirableobjects.oauth.scribe.OauthService

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
                def columns = [['string', 'Name'], ['number', 'Commits']]
                //def chartData = [['Even', 11], ['Marius', 2]]
                GitHubService gitHubService = new GitHubService(githubAccessToken);
                Repository repository = gitHubService.getRepository(user.selectedRepo)

                [selectedRepo: repository,
                        columns: columns, chartData: gitHubService.getRepositoryCommitStats(repository)
                ]
            }
        }
    }

}