package org.peaceful.banana

class SecureController {

    def springSecurityService

    def index() {

    }

    def notes() {
        // Show mooddata or something.
    }

    def users() {
        // List the users and their active team.
        [users: User.findAll(params)]
    }

    def github() {
        /* List github data
         * Count repos
         * Count commits, issues and milestones per repo
         *
         */
    }
}
