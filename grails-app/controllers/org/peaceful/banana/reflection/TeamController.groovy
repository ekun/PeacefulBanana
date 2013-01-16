package org.peaceful.banana.reflection

import org.peaceful.banana.Team
import org.peaceful.banana.TeamUser
import org.peaceful.banana.User
import org.peaceful.banana.git.GitHubService
import org.scribe.model.Token
import uk.co.desirableobjects.oauth.scribe.OauthService

class TeamController {

    def springSecurityService

    GitHubService gitHubService
    OauthService oauthService

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
        Token gitToken = (Token)session[oauthService.findSessionKeyForAccessToken('github')]

        def teams = null

        if (gitToken != null) {
            gitHubService = new GitHubService(gitToken)
            teams = Team.findAllByRepositoryInList(gitHubService.getRepositories().collect {it.getId()}.toList(), params)
        }
        [teams: teams, teamsCount: Team.findAllByRepositoryInList(gitHubService.getRepositories().collect {it.getId()}.toList()).size(), user: user]
    }

    def create() {
        // Show repositories that
        def user = User.get(springSecurityService.principal.id)
    }

    def inspect() {
        def user = User.get(springSecurityService.principal.id)
        def team = user.activeTeam()// By params.id

        // Retrieve all users in the team
        // Check if all of the users have set the correct repo
        [team: team, teamMembers: User.findAllByIdInList(TeamUser.findAllByTeam(team).user.id, params), user: user]
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
}
