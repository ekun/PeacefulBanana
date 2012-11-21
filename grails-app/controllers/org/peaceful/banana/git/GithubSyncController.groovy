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
import org.peaceful.banana.gitdata.IssueEvent
import org.eclipse.egit.github.core.event.IssuesPayload
import org.eclipse.egit.github.core.event.IssueCommentPayload
import org.peaceful.banana.gitdata.IssueComment

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
                firstSave = false
            }

            boolean createFailed = false

            Milestone.findAllByRepository(domainRepo).each {
                it.delete(flush: true)
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
                } catch(ValidationException e){
                    log.error "Milestone exciststs;"
                }
            }

            gitHubService.getIssues(repository, "open").each {
                createFailed = false
                int milestone = 0
                if(it.milestone != null)
                    milestone = it.milestone.number
                try{
                    new Issue(title: it.title,
                        body: it.body,
                        number: it.number,
                        state: it.state,
                        closed: it.closedAt,
                        created: it.createdAt,
                        updated: it.updatedAt,
                        githubId: it.id,
                        repository: domainRepo,
                        milestoneNumber: milestone).save(flush: true, failOnError: true)
                } catch(ValidationException e) {
                    log.error "Updating issue #" + it.number
                    createFailed = true
                }

                if(createFailed) {
                    def issue = Issue.findByRepositoryAndNumber(domainRepo, it.number)
                    issue = Issue.get(issue.id)
                    issue.closed = it.closedAt
                    issue.body = it.body
                    if(it.milestone != null)
                        issue.milestoneNumber = it.milestone.number
                    issue.state = it.state
                    issue.title = it.title
                    try {
                        issue.save(flush: true, failOnError: true)
                    }catch (org.springframework.dao.DataIntegrityViolationException e) {
                        log.error e.message
                    } catch (ValidationException e) {
                        log.error e.message
                    }
                }
            }

            gitHubService.getIssues(repository, "closed").each {
                createFailed = false
                int milestone = 0
                if(it.milestone != null)
                    milestone = it.milestone.number
                try {
                    new Issue(title: it.title,
                        body: it.body,
                        number: it.number,
                        state: it.state,
                        closed: it.closedAt,
                        created: it.createdAt,
                        updated: it.updatedAt,
                        githubId: it.id,
                        repository: domainRepo,
                        milestoneNumber: milestone).save(flush: true, failOnError: true)
                } catch(ValidationException e){
                    log.error "Updating issue #" + it.number
                    createFailed = true
                }

                if(createFailed) {
                    def issue = Issue.findByRepositoryAndNumber(domainRepo, it.number)
                    issue = Issue.get(issue.id)
                    issue.closed = it.closedAt
                    issue.body = it.body
                    if(it.milestone != null)
                        issue.milestoneNumber = it.milestone.number
                    issue.state = it.state
                    issue.title = it.title
                    try {
                        issue.save(flush: true, failOnError: true)
                    }catch (org.springframework.dao.DataIntegrityViolationException e) {
                        log.error e.message
                    } catch (ValidationException e) {
                        log.error e.message
                    }
                }
            }

            gitHubService.getIssueEvents(repository).each {
                try {
                    if(it.type == "IssuesEvent") {
                        new IssueEvent(
                                created: it.createdAt,
                                event: ((IssuesPayload)it.payload).getAction(),
                                login: it.actor.login,
                                githubId: Long.getLong(it.id),
                                issue: Issue.findByGithubId(((IssuesPayload)it.payload).getIssue().id)
                        ).save(flush: true, failOnError: true)
                    } else if(it.type == "IssueCommentEvent") {
                        new IssueComment(
                                login: it.actor.login,
                                githubId: ((IssueCommentPayload)it.payload).comment.id,
                                body: ((IssueCommentPayload)it.payload).comment.body,
                                createdAt: ((IssueCommentPayload)it.payload).comment.createdAt,
                                updatedAt: ((IssueCommentPayload)it.payload).comment.updatedAt,
                                issue: Issue.findByGithubId(((IssueCommentPayload)it.payload).issue.id)
                        ).save(flush: true, failOnError: true)
                    }
                } catch (ValidationException e) {
                    log.error e.message
                }
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

            // So that we get the latest commits.
            domainRepo.updated = repository.updatedAt
            domainRepo.save(flush: true)
        }
        results.get() // wait for async-call
        def table = [update: true]
        render table  as JSON
    }
}
