package org.peaceful.banana

import org.peaceful.banana.reflection.Note

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

class User {

	transient springSecurityService

	String username
	String password
	boolean enabled
    String email
    String firstName = ""
    String lastName = ""
    long selectedRepo
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
    String gitLogin

    static hasMany = [oAuthIDs: OAuthID]

	static constraints = {
		username blank: false, unique: true
		password blank: false
        firstName blank: true, nullable: true
        lastName blank: true, nullable: true
        email email: true
        gitLogin nullable: true
        oAuthIDs nullable: true
	}

	static mapping = {
		password column: '`password`'
	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role } as Set
	}

    List<Notification> getNotifications() {
        Notification.findAllByUserAndCleared(this, false, [sort: "dateCreated", order:'desc', max: '5']) as List
    }

    List<Notification> getAllNotifications() {
        Notification.findAllByUserAndCleared(this, false) as List
    }

    Note getLatestReflectionNote() {
        Note.findByUser(this, [sort: "dateCreated", order:'desc', max: '1'])
    }

    List<Note> getNotes() {
        Note.findAllByUserAndTeam(this, activeTeam()) as List
    }

    Team activeTeam() {
        TeamUser.findByUserAndActive(this, true)?.team
    }

    TeamRole teamRole() {
        TeamUser.findByUserAndActive(this, true)?.role
    }

    def setActiveTeam(Team team) {
        def teamUserOld = TeamUser.findByUserAndTeam(this, this.activeTeam())
        if(teamUserOld) {
            teamUserOld.active = false
            teamUserOld.save()
        }
        def teamUserNew = TeamUser.findByUserAndTeam(this, team)
        teamUserNew.active = true
        teamUserNew.save()

        this.selectedRepo = team.repository
    }

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService.encodePassword(password)
	}

    String toString() {
        return this.firstName + " " + this.lastName
    }
}
