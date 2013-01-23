package org.peaceful.banana.reflection

import org.peaceful.banana.Team
import org.peaceful.banana.TeamRole
import org.peaceful.banana.TeamUser
import org.peaceful.banana.User
import org.peaceful.banana.git.GitHubService
import org.peaceful.banana.git.GithubSyncController
import org.scribe.model.Token
import uk.co.desirableobjects.oauth.scribe.OauthService

class TeamController {

    def springSecurityService

    GitHubService gitHubService
    OauthService oauthService

    static allowedMethods = [ajaxCreateTeam: 'POST']

    def index() {
        // Show dashboard over the users teams
        def user = User.get(springSecurityService.principal.id)
        def team = user.activeTeam()// By params.id
        if(!team)
            redirect(action: 'my')

        // Retrieve all users in the team
        // Check if all of the users have set the correct repo
        [team: team, user: user]
    }

    def my() {
        def user = User.get(springSecurityService.principal.id)

        def teams = Team.findAllByIdInList(TeamUser.findAllByUser(user).collect {it.teamId}.toList(), params)

        Token gitToken = (Token)session[oauthService.findSessionKeyForAccessToken('github')]

        def availibleTeamBasedOnRepos = null
        def repos = null

        if (gitToken != null) {

            gitHubService = new GitHubService(gitToken)

            repos = gitHubService.getRepositories()

            availibleTeamBasedOnRepos = Team.findAllByRepositoryInList(
                    repos.collect {it.id}.toList())
        }

        [teams: teams,
                teamsCount: Team.findAllByOwner(user),
                user: user,
                availibleTeamsBasedOnRepos: availibleTeamBasedOnRepos,
                availTeamCount: Team.countByRepositoryInList(repos.collect {it.id}.toList())]
    }

    def create() {
        // Show repositories that
        def user = User.get(springSecurityService.principal.id)
        Token gitToken = (Token)session[oauthService.findSessionKeyForAccessToken('github')]

        if (gitToken != null) {

            gitHubService = new GitHubService(gitToken)
            [user: user, repositories: gitHubService.getRepositories()]
        } else {
            // Redirect to github-token settings
            // TODO: erstatt denne med en modalboks elns
            redirect(controller: 'settings', action: 'github')
        }
    }

    def inspect() {
        def user = User.get(springSecurityService.principal.id)
        def team = user.activeTeam()// By params.id

        log.error user.teamRole()

        // Retrieve all users in the team
        // Check if all of the users have set the correct repo
        [team: team, teamMembers: TeamUser.findAllByTeam(team, params), user: user]
    }

    def ajaxSwapTeam() {
        def user = User.get(springSecurityService.principal.id)

        // Find team based on id
        def team = TeamUser.findByUserAndTeam(user, Team.get(params.getInt("id")))

        if (team){
            user.setActiveTeam(Team.get(params.getInt("id")))
            user.save()

            render "<div class='alert alert-success'>Team has been switched.</div>"
        } else {
            response.status = 500
            render "<div class='alert alert-error'>Failed to switch team.</div>"
        }
    }

    def ajaxChangeUserTeamRole() {
        def user = User.get(springSecurityService.principal.id)

        // Fetch team-id, team-role and user-id
        if (true) { //TODO: FIX!
            render "<div class='alert alert-success'>Team has been switched.</div>"
        } else {
            response.status = 500
            render "<div class='alert alert-error'>Failed to switch team.</div>"
        }
    }

    def ajaxCreateTeam() {
        // get the logged in user (needed to validate the user.
        def user = User.get(springSecurityService.principal.id)

        // Validate post-data
        def team = params.inputName
        def repo = params.inputRepo

        log.error "[team: '"+team+"', repo: '"+repo+"']"

        if (team && team.length() > 0 && repo) {
            if (Team.countByRepository(repo) != 0) {
                response.status = 500
                render "<div class='alert alert-error'>This repository already has a team.</div>"
            } else {
                // Create the new team.
                def newTeam = new Team(name: team, repository: repo, owner: user)
                newTeam.save(flush:  true)

                // Add the user which created it and add his a manager.
                TeamUser.create user, newTeam, true
                def teamUser = TeamUser.findByUserAndTeam(user, newTeam)
                teamUser.role = TeamRole.MANAGER
                teamUser.save(flush: true)

                // Set the new team as the
                user.setActiveTeam(newTeam)
                user.save()

                new GithubSyncController().sync()

                // Render response
                render "<div class='alert alert-success'>Team has been created.<br>Inspect it <a href='"+createLink(action: 'inspect', id: newTeam.id)+"'>here</a>.</div>"
            }
        } else {
            // Failed validation
            response.status = 500
            render "<div class='alert alert-error'>Please enter valid input.</div>"
        }
    }

    def ajaxGetTeamList() {
        def user = User.get(springSecurityService.principal.id)
        def teams = Team.findAllByIdInList(TeamUser.findAllByUser(user).collect {it.teamId}.toList(), params)

        render(template: "listTeam", model: [teams: teams, user: user])
    }

    def ajaxGetAvailTeamList() {
        def user = User.get(springSecurityService.principal.id)
        Token gitToken = (Token)session[oauthService.findSessionKeyForAccessToken('github')]
        def availibleTeamBasedOnRepos = null
        def repos = null

        if (gitToken != null) {

            gitHubService = new GitHubService(gitToken)

            repos = gitHubService.getRepositories()

            availibleTeamBasedOnRepos = Team.findAllByRepositoryInList(
                    repos.collect {it.id}.toList(), params)
        }
        render(template: "listTeam", model: [teams: availibleTeamBasedOnRepos, user: user])
    }

    def ajaxJoinTeam() {
        def user = User.get(springSecurityService.principal.id)
        def team = Team.findById(params.getLong("id"))

        TeamUser.create user, team, true

        // Set the new team as the
        user.setActiveTeam(team)
        user.save()
    }
}
