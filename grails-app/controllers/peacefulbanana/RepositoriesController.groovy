package peacefulbanana

import org.eclipse.egit.github.core.service.RepositoryService
import org.eclipse.egit.github.core.Repository
import grails.plugins.springsecurity.Secured
import org.eclipse.egit.github.core.client.GitHubClient
import uk.co.desirableobjects.oauth.scribe.OauthService
import org.scribe.model.Token
import org.eclipse.egit.github.core.Contributor
import org.peaceful.banana.git.UserCommitService

class RepositoriesController {

    OauthService oauthService

    @Secured(['ROLE_ADMIN'])
    def index() {

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

            def commits = null
            def ArrayList committers = new ArrayList()
            def selectedRepo
            // find commiters and count
            if (params.getInt("id") != null) {
                selectedRepo = repositories.find {it.id == params.getInt("id")}
                def List<Contributor> collaborators  = repositoryService.getContributors(selectedRepo, false)
                collaborators.unique().each {
                    it -> committers.add([it.login, commitService.getCommits(selectedRepo, it.login).size()])
                }
            }

            [myRepos: repositories.findAll(),
                    selectedRepo: params.getInt("id") != null ? repositories.find {it.id == params.getInt("id")} : null,
                    selectedRepoCommits: params.getInt("id") != null ? commits : null,
                    columns: columns, chartData: committers
            ]
        }
    }

}