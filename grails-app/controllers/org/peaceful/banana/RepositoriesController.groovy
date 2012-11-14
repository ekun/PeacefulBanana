package org.peaceful.banana

import org.peaceful.banana.gitdata.Issue
import org.peaceful.banana.gitdata.Repository
import org.scribe.model.Token
import uk.co.desirableobjects.oauth.scribe.OauthService

class RepositoriesController {
    OauthService oauthService
    def springSecurityService

    def index() {
        def user = User.get(springSecurityService.principal.id)
        if (user.selectedRepo != 0) {

            // if user.access-token for github is not set redirect to /settings/github
            Token githubAccessToken = (Token)session[oauthService.findSessionKeyForAccessToken('github')]
            if (!githubAccessToken) {
                log.debug("Ingen accesstoken satt, redirecter.")
                redirect(controller: 'settings', action: 'github')
            } else {
                //def columns = [['string', 'Name'], ['number', 'Impact']]
                //def chartData = [['Even', 11], ['Marius', 2]]
                def repository = Repository.findByGithubId(user.selectedRepo)

                [selectedRepo: repository]
            }
        }
    }

    def milestone() {
        def user = User.get(springSecurityService.principal.id)
        if (user.selectedRepo != 0) {
            // if user.access-token for github is not set redirect to /settings/github
            Token githubAccessToken = (Token)session[oauthService.findSessionKeyForAccessToken('github')]
            if (!githubAccessToken) {
                log.debug("Ingen accesstoken satt, redirecter.")
                redirect(controller: 'settings', action: 'github')
            } else {
                def repository = Repository.findByGithubId(user.selectedRepo)
                [selectedRepo: repository, milestones: repository.getMilestones()]
            }
        }
    }


    def issue() {
        def user = User.get(springSecurityService.principal.id)
        if (user.selectedRepo != 0) {

            // if user.access-token for github is not set redirect to /settings/github
            Token githubAccessToken = (Token)session[oauthService.findSessionKeyForAccessToken('github')]
            if (!githubAccessToken) {
                log.debug("Ingen accesstoken satt, redirecter.")
                redirect(controller: 'settings', action: 'github')
            } else {
                def repository = Repository.findByGithubId(user.selectedRepo)
                if(!params.getInt("id")) {
                    [selectedRepo: repository, issues: repository.getIssues()]
                } else {
                    [selectedRepo: repository, issue: Issue.findByRepositoryAndNumber(repository,params.getInt("id"))]
                }
            }
        }
    }

    def tagcloud() {
        def user = User.get(springSecurityService.principal.id)
        if (user.selectedRepo != 0) {

            // if user.access-token for github is not set redirect to /settings/github
            Token githubAccessToken = (Token)session[oauthService.findSessionKeyForAccessToken('github')]
            if (!githubAccessToken) {
                log.debug("Ingen accesstoken satt, redirecter.")
                redirect(controller: 'settings', action: 'github')
            } else {
                def repository = Repository.findByGithubId(user.selectedRepo)

                [selectedRepo: repository]
            }
        }
    }

    def statistics() {
        def user = User.get(springSecurityService.principal.id)

        if (user.selectedRepo != 0) {

            // if user.access-token for github is not set redirect to /settings/github
            Token githubAccessToken = (Token)session[oauthService.findSessionKeyForAccessToken('github')]
            if (!githubAccessToken) {
                log.debug("Ingen accesstoken satt, redirecter.")
                redirect(controller: 'settings', action: 'github')
            } else {
                def repository = Repository.findByGithubId(user.selectedRepo)

                [selectedRepo: repository]
            }
        }
    }
}