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
    }

    /**
     * This will show the user every note created by him this last periode so that he can prepair for the workshop
     */
    def preperation() {
        // get the logged in user
        def user = User.get(springSecurityService.principal.id)

        // get the notes he have created and list them
        def loggedInUserNotes = user.getNotes()

        [notes: loggedInUserNotes]
    }
}
