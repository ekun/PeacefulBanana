package org.peaceful.banana

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

class TeamUser implements Serializable {

    User user
    Team team
    TeamRole role = TeamRole.DEVELOPER
    boolean active

    static mapping = {
        id composite:['user','team']
    }

    static constraints = {
    }

    static TeamUser create(User user, Team team, boolean flush = false) {
        new TeamUser(user: user, team: team).save(flush: flush, insert: true)
    }
}
