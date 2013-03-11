package org.peaceful.banana

import org.peaceful.banana.reflection.Note

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
