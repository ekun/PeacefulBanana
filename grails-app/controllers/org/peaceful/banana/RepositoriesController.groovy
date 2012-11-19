package org.peaceful.banana

import org.peaceful.banana.gitdata.Issue
import org.peaceful.banana.gitdata.Repository
import org.peaceful.banana.gitdata.Commit
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

                HashMap<String, Integer> tags = new HashMap<String, Integer>()

                // Generate tagcloud data
                Commit.findAllByRepository(repository).each {
                    if(!it.message.startsWith("Merge branch"))  {
                        it.message.split(' ').each {
                            if(tags.get(it.toLowerCase())){
                                tags.putAt(it.toLowerCase(), tags.get(it.toLowerCase()).intValue()+1)
                            } else {
                                tags.put(it.toLowerCase(),1)
                            }
                        }
                    } else {
                        if(tags.get("merge branch")){
                            tags.putAt("merge branch", tags.get("merge branch").intValue()+1)
                        } else {
                            tags.put("merge branch",1)
                        }
                    }
                }

                [selectedRepo: repository, tags: tags]
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