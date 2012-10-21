package peacefulbanana

import org.eclipse.egit.github.core.service.RepositoryService
import org.eclipse.egit.github.core.Repository
import grails.plugins.springsecurity.Secured
import org.eclipse.egit.github.core.client.GitHubClient

class RepositoriesController {

    @Secured(['ROLE_ADMIN'])
    def index() {
        def RepositoryService service = new RepositoryService(new GitHubClient());
        service.getClient().setCredentials("ekun", "") // TODO: INSERT PASSWORD
        def ArrayList<Repository> repositories = service.getRepositories()

        log.debug("DEBUG" + params.get("id"))

        [myRepos: repositories.findAll(), selectedRepo: params.getInt("id") != null ? repositories.find {it.id == params.getInt("id")} : null]
    }

}
