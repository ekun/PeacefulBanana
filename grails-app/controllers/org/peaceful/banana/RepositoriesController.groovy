package org.peaceful.banana

import org.peaceful.banana.gitdata.Issue
import org.peaceful.banana.gitdata.IssueComment
import org.peaceful.banana.gitdata.IssueEvent
import org.peaceful.banana.gitdata.Milestone
import org.peaceful.banana.gitdata.Repository
import org.peaceful.banana.gitdata.Commit
import org.scribe.model.Token
import uk.co.desirableobjects.oauth.scribe.OauthService
import org.peaceful.banana.git.GitHubService

class RepositoriesController {
    OauthService oauthService
    def springSecurityService

    def index() {
        def user = User.get(springSecurityService.principal.id)

        // if user.access-token for github is not set redirect to /settings/github
        Token githubAccessToken = (Token)session[oauthService.findSessionKeyForAccessToken('github')]
        if (!githubAccessToken) {
            log.debug("Ingen accesstoken satt, redirecter.")
            session.setAttribute("redirect", createLink(controller: 'repositories', action: 'index'))
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
                                        while(it.contains(".") || it.contains(",") || it.contains("!"))
                                            it = it - "." - "!" - ","
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

    def commits() {
        def user = User.get(springSecurityService.principal.id)

        [selectedRepo: Repository.findByGithubId(user.selectedRepo), commits: Commit.findAllByRepository(Repository.findByGithubId(user.selectedRepo), [sort: 'createdAt', order: 'desc'])]
    }

    /**
     * This function is a hard reset for the repository.
     *
     * <b>Magic url, please be aware!</b>
     *“
     * Will load everything stored about the repository freshed.
     */
    def reset() {
        def user = User.get(springSecurityService.principal.id)
        def team = user.activeTeam()

        // if user.access-token for github is not set redirect to /settings/github
        Token githubAccessToken = (Token)session[oauthService.findSessionKeyForAccessToken('github')]
        if (!githubAccessToken) {
            log.debug("Ingen accesstoken satt, redirecter.")
            session.setAttribute("redirect", createLink(controller: 'repositories', action: 'reset'))
            redirect(controller: 'oauth', action: 'github', id: 'authenticate')
        } else {
            // check if the user is owner / manager of the team.
            if (team.owner == user || user.teamRole() == TeamRole.MANAGER) {
                def repo = Repository.findByGithubId(user.selectedRepo)

                Issue.findAllByRepository(repo).each {
                    IssueEvent.findAllByIssue(it).each {
                        it.delete()
                    }
                    IssueComment.findAllByIssue(it).each {
                        it.delete()
                    }
                    it.delete()
                }

                Milestone.findAllByRepository(repo).each {
                    it.delete()
                }

                Commit.deleteAll(Commit.findAllByRepository(repo))

                repo.updated = new Date(0)

                repo.save(flush: true)

                def gitSync = new GitSyncer()
                gitSync.sync(user, githubAccessToken)

                render "Wipe completed, currently syncing."
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

            HashMap<String, Integer> tags = new HashMap<String, Integer>()
            HashMap<String, Integer> myTags = new HashMap<String, Integer>()

            // Generate tagcloud data
            Commit.findAllByRepository(repository).each {
                it.message.split(" ").each {
                    while(it.contains(".") || it.contains(",") || it.contains("!"))
                        it = it - "." - "!" - ","
                    if(it.startsWith("#")) {
                        if(tags.get(it.toLowerCase())){
                            tags.putAt(it.toLowerCase(), tags.get(it.toLowerCase()).intValue()+1)
                        } else {
                            tags.put(it.toLowerCase(),1)
                        }
                    }
                }
            }


            // Generate tagcloud data
            Commit.findAllByRepositoryAndLogin(repository, gitHubService.getAuthenticatedUser().getLogin()).each {
                it.message.split(" ").each {
                    if(it.startsWith("#")) {
                        while(it.contains(".") || it.contains(",") || it.contains("!"))
                            it = it - "." - "!" - ","
                        if(myTags.get(it.toLowerCase())){
                            myTags.putAt(it.toLowerCase(), myTags.get(it.toLowerCase()).intValue()+1)
                        } else {
                            myTags.put(it.toLowerCase(),1)
                        }
                    }
                }
            }

            [selectedRepo: repository, tags: tags, mytags: myTags]
        }
    }
}