package org.peaceful.banana

import org.peaceful.banana.gitdata.Issue
import org.peaceful.banana.gitdata.Repository
import org.peaceful.banana.gitdata.Commit
import org.scribe.model.Token
import uk.co.desirableobjects.oauth.scribe.OauthService
import org.peaceful.banana.git.GitHubService
import org.peaceful.banana.git.util.WordProcessor

class RepositoriesController {
    OauthService oauthService
    def springSecurityService

    def index() {
        def user = User.get(springSecurityService.principal.id)

        // if user.access-token for github is not set redirect to /settings/github
        Token githubAccessToken = (Token)session[oauthService.findSessionKeyForAccessToken('github')]
        if (!githubAccessToken) {
            log.debug("Ingen accesstoken satt, redirecter.")
            session.setAttribute("redirect", createLink(controller: 'repositories'))
            redirect(controller: 'oauth', action: 'github', id: 'authenticate')
        } else {
            if (!user.activeTeam())
                redirect(controller: 'team')
            //def columns = [['string', 'Name'], ['number', 'Impact']]
            //def chartData = [['Even', 11], ['Marius', 2]]
            def repository = Repository.findByGithubId(user?.selectedRepo)

            [selectedRepo: repository]
        }
    }

    def milestone() {
        def user = User.get(springSecurityService.principal.id)
        // if user.access-token for github is not set redirect to /settings/github
        Token githubAccessToken = (Token)session[oauthService.findSessionKeyForAccessToken('github')]
        if (!githubAccessToken) {
            log.debug("Ingen accesstoken satt, redirecter.")
            session.setAttribute("redirect", createLink(controller: 'repositories', id: 'milestone'))
            redirect(controller: 'oauth', action: 'github', id: 'authenticate')
        } else {
            if (!user.activeTeam())
                redirect(controller: 'team')
            def repository = Repository.findByGithubId(user?.selectedRepo)
            def milestones = repository.getMilestones().findAll {
                it.state == "open"
            }

            def selectedMilestone = null
            def teamTags = new HashMap<String, Integer>()

            if (params.get("id") == "overdue") {
                milestones = repository.getMilestones().findAll {
                    it.state == "open" && it.dueOn != null && it.dueOn.before(new Date(System.currentTimeMillis()))
                }
            } else if (params.get("id") == "closed") {
                milestones = repository.getMilestones().findAll {
                    it.state == "closed"
                }
            } else if (params.get("id") == "all") {
                milestones = repository.getMilestones()
            } else if (params.get("id") != null) {
                selectedMilestone = repository.getMilestones().find {
                    it.id == params.getLong("id")
                }

                if(selectedMilestone) {
                    selectedMilestone.issues.each {
                        if(it.commits.size() > 0) {
                            it.commits.each {
                                it.message.split(" ").each {
                                    if(it.startsWith("#")) {
                                        if(teamTags.get(it.toLowerCase())){
                                            teamTags.putAt(it.toLowerCase(), teamTags.get(it.toLowerCase()).intValue()+1)
                                        } else {
                                            teamTags.put(it.toLowerCase(),1)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            [selectedRepo: repository, milestones: milestones, selectedMilestone: selectedMilestone, teamTags: teamTags.sort {-it.value}]
        }
    }


    def issue() {

        def user = User.get(springSecurityService.principal.id)

        // if user.access-token for github is not set redirect to /settings/github
        Token githubAccessToken = (Token)session[oauthService.findSessionKeyForAccessToken('github')]
        if (!githubAccessToken) {
            log.debug("Ingen accesstoken satt, redirecter.")
            session.setAttribute("redirect", createLink(controller: 'repositories', action: 'issue'))
            redirect(controller: 'oauth', action: 'github', id: 'authenticate')
        } else {
            if (!user.activeTeam())
                redirect(controller: 'team')
            def repository = Repository.findByGithubId(user.selectedRepo)
            if(!params.getInt("id")) {
                [selectedRepo: repository, issues: repository.getIssues()]
            } else {
                [selectedRepo: repository, issue: Issue.findByRepositoryAndGithubId(repository,params.getInt("id"))]
            }
        }
    }

    def tagcloud() {
        def user = User.get(springSecurityService.principal.id)

        // if user.access-token for github is not set redirect to /settings/github
        Token githubAccessToken = (Token)session[oauthService.findSessionKeyForAccessToken('github')]
        if (!githubAccessToken) {
            log.debug("Ingen accesstoken satt, redirecter.")
            session.setAttribute("redirect", createLink(controller: 'repositories', action: 'tagcloud'))
            redirect(controller: 'oauth', action: 'github', id: 'authenticate')
        } else {
            if (!user.activeTeam())
                redirect(controller: 'team')

            def gitHubService = new GitHubService(githubAccessToken)
            def repository = Repository.findByGithubId(user.selectedRepo)
            WordProcessor wordProcessor = new WordProcessor();

            HashMap<String, Integer> tags = new HashMap<String, Integer>()
            HashMap<String, Integer> myTags = new HashMap<String, Integer>()

            // Generate tagcloud data
            Commit.findAllByRepository(repository).each {
                if(!it.message.startsWith("Merge branch"))  {
                    it.message.replaceAll("[-+.^:,]","").split(' ').each {
                        if(!wordProcessor.isStopWord(it.toLowerCase())) {
                            if(tags.get(it.toLowerCase())){
                                tags.putAt(it.toLowerCase(), tags.get(it.toLowerCase()).intValue()+1)
                            } else {
                                tags.put(it.toLowerCase(),1)
                            }
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


            // Generate tagcloud data
            Commit.findAllByRepositoryAndLogin(repository, gitHubService.getAuthenticatedUser().getLogin()).each {
                if(!it.message.startsWith("Merge branch"))  {
                    it.message.replaceAll("[-+.^:,]","").split(' ').each {
                        if(!wordProcessor.isStopWord(it.toLowerCase())) {
                            if(myTags.get(it.toLowerCase())){
                                myTags.putAt(it.toLowerCase(), myTags.get(it.toLowerCase()).intValue()+1)
                            } else {
                                myTags.put(it.toLowerCase(),1)
                            }
                        }
                    }
                } else {
                    if(myTags.get("merge branch")){
                        myTags.putAt("merge branch", myTags.get("merge branch").intValue()+1)
                    } else {
                        myTags.put("merge branch",1)
                    }
                }
            }

            [selectedRepo: repository, tags: tags, mytags: myTags]
        }
    }
}