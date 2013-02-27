package org.peaceful.banana

import grails.validation.ValidationException
import groovy.time.TimeCategory
import org.eclipse.egit.github.core.event.IssueCommentPayload
import org.eclipse.egit.github.core.event.IssuesPayload
import org.peaceful.banana.git.GitHubService
import org.peaceful.banana.gitdata.Commit
import org.peaceful.banana.gitdata.Issue
import org.peaceful.banana.gitdata.IssueComment
import org.peaceful.banana.gitdata.IssueEvent
import org.peaceful.banana.gitdata.Milestone
import org.peaceful.banana.gitdata.Repository
import org.scribe.model.Token

/**
 * This class will sync with github
 * Created to be run as async.
 *
 * Creator: ekun
 * Date: 12.02.13
 * Time: 12:43
 */
class GitSyncer {
    void sync(User user, Token gitToken) {
        GitHubService gitHubService = new GitHubService(gitToken)
        def repository = gitHubService.getRepository(user.selectedRepo)

        def domainRepo = new Repository(name: repository.name,
                description: repository.description, githubId: repository.id,
                created: repository.createdAt, updated: repository.updatedAt)
        boolean firstSave = true
        try {
            domainRepo.save(failOnError: true)
        } catch(ValidationException e) {
            domainRepo = Repository.findByGithubId(user.selectedRepo)
            firstSave = false
        }

        boolean createFailed = false

        Milestone.findAllByRepository(domainRepo).each {
            it.delete(flush: true)
        }

        gitHubService.getMilestones(repository, true).each {
            new Milestone(title: it.title,
                    description: it.description,
                    number: it.number,
                    repository: domainRepo,
                    dueOn: it.dueOn,
                    created: it.createdAt,
                    creator: it.creator.login,
                    state: it.state).save(flush: true)
        }

        gitHubService.getMilestones(repository, false).each {
            new Milestone(title: it.title,
                    description: it.description,
                    number: it.number,
                    repository: domainRepo,
                    dueOn: it.dueOn,
                    created: it.createdAt,
                    creator: it.creator.login,
                    state: it.state).save(flush: true)
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

                // SAVING!
                issue.save(flush: true)
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

                // SAVING IT!
                issue.save(flush: true)
            }
        }

        gitHubService.getIssueEvents(repository).each {
            try {
                if(it.type == "IssuesEvent") {
                    new IssueEvent(
                            created: it.createdAt,
                            event: ((IssuesPayload)it.payload).getAction(),
                            login: it.actor.login,
                            githubId: it.id,
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
                if(it.type == "IssueCommentEvent") {
                    def issueComment = IssueComment.findByGithubId(((IssueCommentPayload)it.payload).comment.id)
                    issueComment.body = ((IssueCommentPayload)it.payload).comment.body
                    issueComment.updatedAt = ((IssueCommentPayload)it.payload).comment.updatedAt
                    issueComment.save(flush: true)
                }
            }
        }

        Date lastUpdate = null
        if(!firstSave) {
            use ( TimeCategory ) {
                // This will get all commits done AFTER the last one, not including the last one second time.
                lastUpdate = domainRepo.updated + 1.seconds
            }
        } else {
            // When first syncing with github, set the users name.
            // Set the users name
            def nameUser = gitHubService.authenticatedUser.name.split(" ")
            user.firstName = ""

            for (int i = 0; i < nameUser.length -2; i ++) {
                user.firstName += nameUser[i].capitalize() + " "
            }
            user.firstName = user.firstName.substring(0, user.firstName.length() - 1);

            user.lastName = nameUser[nameUser.length-1] // Lastname
            user.lastName.capitalize()

            user.save()
        }

        gitHubService.getCommitsSince(repository, domainRepo.lastCommit()).each {
            new Commit(login: it.user,
                    message: it.message,
                    additions: it.added,
                    deletions: it.deleted,
                    total: it.impact,
                    createdAt: it.created,
                    repository: domainRepo).save(flush: true)
        }

        // So that we get the latest commits.
        domainRepo.updated = repository.updatedAt
        domainRepo.save(flush: true)
    }
}
