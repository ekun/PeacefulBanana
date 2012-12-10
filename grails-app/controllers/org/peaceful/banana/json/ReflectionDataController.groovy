package org.peaceful.banana.json

import grails.converters.JSON
import org.peaceful.banana.User
import org.peaceful.banana.reflection.Note

/**
 * Class to gather JSON-data relevant to reflection
 */
class ReflectionDataController {

    def springSecurityService

    def index() {}

    /**
     * Will gather a team-average and your own mood
     *
     * @return
     */
    def mood() {
        def user = User.get(springSecurityService.principal.id)

        def columns = []
        columns << [label: 'Date', type: 'string']
        columns << [label: user.firstName + " " + user.lastName, type: 'number']

        // Team mood blir da kolonner slik:
        // columns << [label: username1, type: 'number']
        // columns << [label: username2, type: 'number']

        def rows = []
        def cells

        // Gather mood-data with timestamps
        Note.findAllByUser(user).each {
            cells = []
            cells << [v: it.dateCreated.dateString] << [v: it.mood]
            rows << ['c': cells]
        }
        /**
         * Gather team average
         TODO: hvordan regnet ut snittet av mood for teamet om de har forskjellige datoer osv.
         Kan regne ut mood avg. per bruker først, for så å bruke dette til å finne team avg. Så lenge det er over
         en 2 ukers periode så vil det bli et greit gjennomsnitt. Kan ha en minimum av si 5 notes for å telles med i team avg.
         */


        def table = [cols: columns, rows: rows]

        render table as JSON
    }
}
