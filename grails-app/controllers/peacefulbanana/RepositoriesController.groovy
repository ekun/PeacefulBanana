package peacefulbanana

import org.eclipse.egit.github.core.service.RepositoryService
import org.eclipse.egit.github.core.Repository

class RepositoriesController {
	def authenticationService

    def index() {

		if (!authenticationService.isLoggedIn(request)) {
			// Redirect or return Forbidden
			response.sendError(403)
        }
		else {
            def RepositoryService service = new RepositoryService();
            def ArrayList<Repository> repositories = service.getRepositories("tkbremnes")
            [myRepos: repositories.findAll()]
		}

    }
}
