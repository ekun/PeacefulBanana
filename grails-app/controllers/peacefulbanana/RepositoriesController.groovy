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

        def RepositoryService repositoryService = new RepositoryService(new GitHubClient());
        Token githubAccessToken = session[oauthService.findSessionKeyForAccessToken('github')]
        repositoryService.getClient().setOAuth2Token(githubAccessToken.token)
        def CommitService commitService = new CommitService(repositoryService.getClient())

        def ArrayList<Repository> repositories = repositoryService.getRepositories()

        log.debug("DEBUG" + params.get("id"))

        [myRepos: repositories.findAll(),
                selectedRepo: params.getInt("id") != null ? repositories.find {it.id == params.getInt("id")} : null,
                selectedRepoCommits: params.getInt("id") != null ? commitService.getCommits(repositories.find {it.id == params.getInt("id")}) : null
        ]
    }

}
