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
        columns << [label: 'Date', type: 'date']
        columns << [label: 'User', type: 'string']
        columns << [label: 'Mood', type: 'number']

        def rows = []
        def cells

        // Gather mood-data with timestamps
        Note.findAllByUser(user).each {
            cells = []
            cells << [v: new Date(it.dateCreated.dateString)] << [v: it.user.firstName + " " + it.user.lastName] << [v: it.mood]
            rows << ['c': cells]
        }
        // Gather team average
        // TODO: hvordan regnet ut snittet av mood for teamet om de har forskjellige datoer osv.

        def table = [cols: columns, rows: rows]

        render table as JSON
    }
}
