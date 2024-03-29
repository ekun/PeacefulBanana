package org.peaceful.banana

import grails.validation.ValidationException
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
 This file is part of Peaceful Banana.

 Peaceful Banana is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 Peaceful Banana is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with Peaceful Banana.  If not, see <http://www.gnu.org/licenses/>.
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

        if(firstSave) {
            // Set the date to first available date. This will retrieve every commit.
            domainRepo.updated = new Date(0) // 1-1-1970 00:00:00.0
        }

        // So that we only get the latest
        gitHubService.getCommitsSince(repository, domainRepo.updated).each {
            new Commit(login: it.user,
                    message: it.message,
                    additions: it.added,
                    deletions: it.deleted,
                    total: it.impact,
                    createdAt: it.created,
                    repository: domainRepo).save()
        }

        // Updating the timestamp where the
        domainRepo.updated = repository.updatedAt

        domainRepo.save(flush: true)
    }
}
