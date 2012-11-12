package org.peaceful.banana.git

import org.peaceful.banana.User
import org.scribe.model.Token
import uk.co.desirableobjects.oauth.scribe.OauthService
import org.peaceful.banana.gitdata.Repository
import grails.converters.JSON
import org.peaceful.banana.gitdata.Commit
import org.peaceful.banana.gitdata.Issue
import grails.validation.ValidationException
import org.peaceful.banana.gitdata.Milestone

class GithubSyncController {

    def springSecurityService
    OauthService oauthService

    def index() {
        // Check if there is anything new to fetch
        if (!session["lastCheck"]){
            // if not set this will set it!
            session["lastCheck"] = System.currentTimeMillis()-60001;
        }

        def user = User.get(springSecurityService.principal.id)

        if(user?.selectedRepo && session[oauthService.findSessionKeyForAccessToken('github')]
                && new Date((long)session["lastCheck"]).before(new Date(System.currentTimeMillis()-60000))) {
            session["lastCheck"] = System.currentTimeMillis()

            GitHubService gitHubService = new GitHubService((Token)session[oauthService.findSessionKeyForAccessToken('github')])
            def table = [update: gitHubService.getRepository(user.selectedRepo).updatedAt.after(Repository.findByGithubId(user.selectedRepo).updated)]

            render table  as JSON
        } else {
            def table = [update: false]
            render table  as JSON
        }
    }

    def sync() {
        def user = User.get(springSecurityService.principal.id)
        // runAsync or callAsync?
        GitHubService gitHubService = new GitHubService((Token)session[oauthService.findSessionKeyForAccessToken('github')])
        def repository = gitHubService.getRepository(user.selectedRepo)

        session["lastCheck"] = System.currentTimeMillis()

        def results = callAsync {
            def domainRepo = new Repository(name: repository.name,
                    description: repository.description, githubId: repository.id,
                    created: repository.createdAt, updated: repository.updatedAt)
            boolean firstSave = true
            try {
                domainRepo.save(failOnError: true)
            } catch(ValidationException e) {
                log.error "Repository excists."
                domainRepo = Repository.findByGithubId(user.selectedRepo)
                domainRepo.updated = repository.updatedAt
                domainRepo.save(flush: true)
                firstSave = false
            }

            gitHubService.getMilestones(repository, true).each {
                 try {
                    new Milestone(title: it.title,
                        description: it.description,
                        number: it.number,
                        repository: domainRepo,
                        dueOn: it.dueOn,
                        created: it.createdAt,
                        creator: it.creator.login,
                        state: it.state).save(flush: true, failOnError: true)
                 } catch(Exception e){
                     log.error "Milestone exciststs; " + e.message
                 }
            }

            gitHubService.getMilestones(repository, false).each {
                try{
                    new Milestone(title: it.title,
                        description: it.description,
                        number: it.number,
                        repository: domainRepo,
                        dueOn: it.dueOn,
                        created: it.createdAt,
                        creator: it.creator.login,
                        state: it.state).save(flush: true, failOnError: true)
                } catch(Exception e){
                    log.error "Milestone exciststs; " + e.message
                }
            }

            gitHubService.getIssues(repository).each {
                def milestone = Milestone.findByRepositoryAndNumber(domainRepo, it.milestone.number)

                log.error "Milestone: " + milestone + " - " + it.milestone.number

                new Issue(title: it.title,
                        body: it.body,
                        number: it.number,
                        state: it.state,
                        closed: it.closedAt,
                        created: it.createdAt,
                        updated: it.updatedAt,
                        githubId: it.id,
                        repository: domainRepo,
                        milestone: milestone).save(flush: true)
            }

            Date lastUpdate = null
            if(!firstSave)
                lastUpdate = domainRepo.updated

            gitHubService.getCommitsSince(repository, lastUpdate).each {
                try {
                    new Commit(login: it.user,
                        message: it.message,
                        additions: it.added,
                        deletions: it.deleted,
                        total: it.impact,
                        repository: domainRepo).save(flush: true, failOnError: true)
                } catch(ValidationException e) {
                    log.error e.message
                }
            }

        }
        results.get()
        def table = [update: true]
        render table  as JSON
    }
}
