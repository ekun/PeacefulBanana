package peacefulbanana

import org.eclipse.egit.github.core.service.RepositoryService
import org.eclipse.egit.github.core.Repository
import grails.plugins.springsecurity.Secured

class RepositoriesController {

    @Secured(['ROLE_ADMIN'])
    def index() {
        def RepositoryService service = new RepositoryService();
        def ArrayList<Repository> repositories = service.getRepositories("tkbremnes")
        [myRepos: repositories.findAll()]
    }

}
