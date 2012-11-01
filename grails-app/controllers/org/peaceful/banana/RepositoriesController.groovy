package org.peaceful.banana

import org.eclipse.egit.github.core.service.RepositoryService
import org.eclipse.egit.github.core.Repository
import org.eclipse.egit.github.core.client.GitHubClient
import uk.co.desirableobjects.oauth.scribe.OauthService
import org.scribe.model.Token
import org.eclipse.egit.github.core.Contributor
import org.peaceful.banana.git.UserCommitService

class RepositoriesController {
    OauthService oauthService
    def springSecurityService

    def index() {

        def user = User.get(springSecurityService.principal.id)
        log.error(user.selectedRepo)
        if (user.selectedRepo != 0) {

            // if user.access-token for github is not set redirect to /settings/github
            Token githubAccessToken = (Token)session[oauthService.findSessionKeyForAccessToken('github')]
            if (!githubAccessToken) {
                log.debug("Ingen accesstoken satt, redirecter.")
                redirect(controller: 'settings', action: 'github')
            } else {
                def columns = [['string', 'Name'], ['number', 'Commits']]
                //def chartData = [['Even', 11], ['Marius', 2]]
                def RepositoryService repositoryService = new RepositoryService(new GitHubClient())
                repositoryService.getClient().setOAuth2Token(githubAccessToken.token)

                def UserCommitService commitService = new UserCommitService(repositoryService.getClient())
                def ArrayList<Repository> repositories = repositoryService.getRepositories()

                def ArrayList committers = new ArrayList()
                // find commiters and count
                def selectedRepo = repositories.find {it.id == user.selectedRepo}
                def List<Contributor> collaborators  = repositoryService.getContributors(selectedRepo, true)
                collaborators.each {
                    it ->
                    if (it.login != null) {
                        committers.add([it.login, commitService.getCommits(selectedRepo, it.login).size()])
                    } else {
                        committers.add([it.name, commitService.getCommits(selectedRepo, it.name).size()])
                    }
                }

                [selectedRepo: selectedRepo,
                        columns: columns, chartData: committers
                ]
            }
        }
    }

}