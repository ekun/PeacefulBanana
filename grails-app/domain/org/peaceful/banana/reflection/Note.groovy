package org.peaceful.banana.reflection

import org.peaceful.banana.Team
import org.peaceful.banana.User
import grails.validation.Validateable

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

@Validateable
class Note {

    int mood
    String contributions
    String improvements
    Date dateCreated // should be set by GORM
    boolean shared = false
    Team team

    static belongsTo = [user: User]

    static constraints = {
        mood range: 1..100, nullable: false
        contributions blank: false, nullable: false
        improvements blank: false, nullable: false
    }

    static mapping = {
        contributions type: "text"
        improvements type: "text"
    }
}




