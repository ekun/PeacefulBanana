package org.peaceful.banana

import org.eclipse.egit.github.core.service.UserService
import org.scribe.model.Token
import org.eclipse.egit.github.core.User
import org.eclipse.egit.github.core.service.RepositoryService
import org.peaceful.banana.git.GitHubService
import org.eclipse.egit.github.core.Repository
import org.eclipse.egit.github.core.Issue

class SettingsController {

    static allowedMethods = [changeSelectedRepo: 'POST']

    def GithubController githubController = new GithubController()
    GitHubService gitHubService

    def index() {
        Token gitToken = githubController.getToken()
        def user = githubController.getPrincipal()
        def User gitUser = null

        if (gitToken != null) {
            def UserService gitOAuthService = githubController.setOAuthToken(gitToken.token)
            gitHubService = new GitHubService(gitToken)

            if (gitOAuthService.getUser() != null) {
                gitUser = gitOAuthService.getUser()
            }
            [user: user, gitUser: gitUser, repository: gitHubService.getRepository(user.selectedRepo)]
        }
    }

    def github() {
        Token gitToken = githubController.getToken()
        def user = githubController.getPrincipal()
        def User gitUser = null

        if (gitToken != null) {
            def UserService gitOAuthService = githubController.setOAuthToken(gitToken.token)
            def RepositoryService repositoryService = new RepositoryService(gitOAuthService.getClient())
            if (gitOAuthService.getUser() != null) {
                gitUser = gitOAuthService.getUser()
            }
            [user: user, gitUser: gitUser, repositories: repositoryService.getRepositories()]
        }
    }
    /**
     * method to changeSelectedRepo in settings
     * this could maybe be moved into githubcontroller, but it is settings specific so it might aswell be here =)
     * @return
     */

    def changeSelectedRepo() {
        def user = githubController.getPrincipal()
        Token gitToken = githubController.getToken()
        user?.selectedRepo = params.getLong("repoSelection")
        user.save()

        runAsync {
            gitHubService = new GitHubService(gitToken)

            Repository repository = gitHubService.getRepository(user.selectedRepo)
            org.peaceful.banana.gitdata.Repository domainRepo

            domainRepo = new org.peaceful.banana.gitdata.Repository(name: repository.name,
                    description: repository.description, githubId: repository.id,
                    created: repository.createdAt, updated: repository.updatedAt)

            domainRepo.save(flush: true)

            List<Issue> issueList = gitHubService.getIssues(repository)
            issueList.each {
                new org.peaceful.banana.gitdata.Issue(title: it.title, body: it.body, number: it.number, state: it.state,
                        closed: it.closedAt, created: it.createdAt, updated: it.updatedAt, githubId: it.id, repository: domainRepo).save(flush: true)
            }
        }

        render(user.selectedRepo)
    }
}
