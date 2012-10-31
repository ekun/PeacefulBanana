package peacefulbanana

import org.eclipse.egit.github.core.service.RepositoryService
import org.eclipse.egit.github.core.Repository
import grails.plugins.springsecurity.Secured
import org.eclipse.egit.github.core.client.GitHubClient
import org.eclipse.egit.github.core.service.CommitService
import uk.co.desirableobjects.oauth.scribe.OauthService
import org.scribe.model.Token

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

            def CommitService commitService = new CommitService(repositoryService.getClient())
            def ArrayList<Repository> repositories = repositoryService.getRepositories()

            def commits = null
            def ArrayList committers = new ArrayList()
            // find commiters and count
            if (params.getInt("id") != null) {
                commits = commitService.getCommits(repositories.find {it.id == params.getInt("id")})
                def ArrayList<String> tempCommitters = new ArrayList<String>()
                commits.each {
                    it -> tempCommitters.add(it.commit.author.name)
                }
                tempCommitters.unique(false).each {
                    it -> committers.add([it, tempCommitters.count(it)])
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