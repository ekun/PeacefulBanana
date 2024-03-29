package org.peaceful.banana.reflection

import org.peaceful.banana.GitSyncer
import org.peaceful.banana.Notification
import org.peaceful.banana.NotificationType
import org.peaceful.banana.Team
import org.peaceful.banana.TeamRole
import org.peaceful.banana.TeamUser
import org.peaceful.banana.User
import org.peaceful.banana.git.GitHubService
import org.scribe.model.Token
import uk.co.desirableobjects.oauth.scribe.OauthService

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

class TeamController {

    def springSecurityService

    GitHubService gitHubService
    OauthService oauthService

    static allowedMethods = [ajaxCreateTeam: 'POST']

    def index() {
        // Show dashboard over the users teams
        def user = User.get(springSecurityService.principal.id)
        def teams = Team.findAllByIdInList(TeamUser.findAllByUser(user).collect {it.teamId}.toList(), params)

        Token gitToken = (Token)session[oauthService.findSessionKeyForAccessToken('github')]

        def availibleTeamBasedOnRepos = null
        def availibleTeamBasedOnReposCount = 0
        def repos = null

        if (gitToken != null) {
            // if the token is set
            gitHubService = new GitHubService(gitToken)

            repos = gitHubService.getRepositories()

            availibleTeamBasedOnRepos = Team.findAllByRepositoryInList(
                    repos.collect {it.id}.toList())
            availibleTeamBasedOnReposCount = Team.countByRepositoryInList(repos.collect {it.id}.toList())
        } else {
            session.setAttribute("redirect", createLink(controller: 'team', action: 'index'))
            redirect(controller: 'oauth', action: 'github', id: 'authenticate')
        }

        [teams: teams,
                teamsCount: Team.findAllByOwner(user),
                user: user,
                availibleTeamsBasedOnRepos: availibleTeamBasedOnRepos,
                availTeamCount: availibleTeamBasedOnReposCount]
    }

    def create() {
        // Show repositories that
        def user = User.get(springSecurityService.principal.id)
        Token gitToken = (Token)session[oauthService.findSessionKeyForAccessToken('github')]

        if (gitToken != null) {
            gitHubService = new GitHubService(gitToken)
        } else {
            session.setAttribute("redirect", createLink(controller: 'team', action: 'create'))
            redirect(controller: 'oauth', action: 'github', id: 'authenticate')
        }
        [user: user, repositories: gitHubService?.getRepositories()]
    }

    def inspect() {
        def user = User.get(springSecurityService.principal.id)
        def team = Team.get(params.getInt("id"))// By params.id

        if (!team.members.contains(user)){
            redirect(controller: 'team', action: 'index')
        }


        // Retrieve all users in the team
        // Check if all of the users have set the correct repo
        [team: team, teamMembers: TeamUser.findAllByTeam(team, params), user: user]
    }

    def ajaxSwapTeam() {
        def user = User.get(springSecurityService.principal.id)

        // Find team based on id
        def team = TeamUser.findByUserAndTeam(user, Team.get(params.getInt("id")))
        String output = ""

        if (team != null){
            user.setActiveTeam(Team.get(params.getInt("id")))
            user.save()

            output = "<div class='alert alert-success'>Team has been switched.</div>"
        } else {
            response.status = 500
            output = "<div class='alert alert-error'>Failed to switch team.</div>"
        }
        render output
    }

    def ajaxChangeUserTeamRole() {
        def user = User.get(springSecurityService.principal.id)
        def team = Team.findById(params.getLong("teamId"))
        def teamRole = TeamUser.findByUserAndTeam(user, team)
        def userToBeChanged = User.findById(params.getLong("userId"))
        def roleNameField = params.get("teamRole").toString()
        def success = false

        // Fetch team-id, team-role and user-id
        if (teamRole.role == TeamRole.MANAGER || team.owner == user) {

            if ((teamRole.role != TeamRole.MANAGER && team.owner != userToBeChanged) ||
                    team.owner == user) {

                def temp = TeamUser.findByTeamAndUser(team, userToBeChanged)

                temp.role = TeamRole."$roleNameField"

                temp.save()

                success = true
            }
        }

        if (success)
            render "<div class='alert alert-success'>The role of "+userToBeChanged.username+" has been changed to "+roleNameField+".</div>"
        else {
            response.status = 500
            render "<div class='alert alert-error'>You do not have the rights to change this users team role.</div>"
        }
    }

    def ajaxCreateTeam() {
        // get the logged in user (needed to validate the user.
        def user = User.get(springSecurityService.principal.id)

        // Validate post-data
        def team = params.inputName
        def repo = params.inputRepo

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
                user.save(flush: true)

                new Notification(user: user,
                        title: "Congratulations",
                        body: "You have created a team!<br><br>",
                        notificationType: NotificationType.OTHER).save(flush: true)

                // Run repo sync
                def gitSync = new GitSyncer()
                gitSync.sync(user, (Token)session[oauthService.findSessionKeyForAccessToken('github')])

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
        render(template: "listAvailTeam", model: [teams: availibleTeamBasedOnRepos, user: user])
    }

    def ajaxGetTeamMemberList() {
        def team = Team.findById(params.getInt("id"))
        def user = User.get(springSecurityService.principal.id)

        render(template: "listMember", model: [users: TeamUser.findAllByTeam(team, params), user: user])
    }

    def ajaxJoinTeam() {
        def user = User.get(springSecurityService.principal.id)
        def team = Team.findById(params.getLong("id"))

        TeamUser.create user, team, true

        // Set the new team as the
        user.setActiveTeam(team)
        user.save()

        new Notification(user: user, title: "Congratulations", body: "You have joined a team!", notificationType: NotificationType.OTHER).save(flush: true)

        render "<div class='alert alert-success'>Team has been joined.</div>"
    }
}
