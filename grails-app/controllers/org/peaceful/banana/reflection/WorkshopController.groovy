package org.peaceful.banana.reflection

import org.peaceful.banana.User

class WorkshopController {

    def springSecurityService

    /**
     * Will show the upcomming workshop(s)
     *
     * TODO: Anything else we want to show here?
     */
    def index() {
        // get the logged in user
        def user = User.get(springSecurityService.principal.id)

        [workshops: user.activeTeam().workshops]
    }

    def create() {
        // get the logged in user
        def user = User.get(springSecurityService.principal.id)
    }
}
