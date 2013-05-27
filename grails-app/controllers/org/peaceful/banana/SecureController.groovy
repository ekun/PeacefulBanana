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
class SecureController {

    def springSecurityService

    def index() {

    }

    def notes() {
        // Show mooddata or something.
        [notes: Note.findAll(params), allNotes: Note.count()]
    }

    def users() {
        // List the users and their active team.
        [users: User.findAll(params), allUsers: User.count()]
    }

    def github() {
        /* List github data
         * Count repos
         * Count commits, issues and milestones per repo
         *
         */
    }
}
