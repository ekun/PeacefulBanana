package org.peaceful.banana.reflection

import org.peaceful.banana.Team
import org.peaceful.banana.TeamUser
import org.peaceful.banana.User

class TeamController {

    def springSecurityService

    def index() {
        // Show dashboard over the users teams
        def user = User.get(springSecurityService.principal.id)
        def team = user.activeTeam()// By params.id

        // Retrieve all users in the team
        // Check if all of the users have set the correct repo
        [team: team, user: user]
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
            user.setActiveTeam(team)
            user.save()

            render "<div class='alert alert-success'>Team has been switched.</div>"
        } else {
            response.status = 500
            render "<div class='alert alert-error'>Failed to switch team.</div>"
        }
    }
}
