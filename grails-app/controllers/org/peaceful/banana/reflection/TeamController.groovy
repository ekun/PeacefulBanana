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

        def teams = Team.findById(TeamUser.findByUser(user).teamId, params)

        [teams: teams, teamsCount: Team.findAllByOwner(user), user: user]
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
        String team = params.inputName
        def repo = params.inputRepo

        log.error "[team: '"+team+"', repo: '"+repo+"']"

        if (!team.empty && team.length() > 0 && !repo.empty) {

        } else {

        }


        render "<div class='alert alert-success'>Team has been created.</div>"
    }
}
