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

        def teamMember = user.activeTeam().members

        def columns = []
        columns << [label: 'Date', type: 'string']
        teamMember.each {
            columns << [label: it.firstName + " " + it.lastName, type: 'number']
        }

        def rows = []
        def cells

        def i = 0
        def memberNr = 0
        // Gather mood-data with timestampss
        teamMember.each {
            i = 0
            Note.findAllByUser(it, [sort: "dateCreated", order:'asc']).each {
                cells = []
                if (memberNr == 0){
                    cells << [v:  it.dateCreated.dateString] << [v: it.mood]
                    rows << ['c': cells]
                } else {
                    if (rows.size() <= i) {
                        if (!cells.empty)
                            cells.clear()
                        cells << [v:  it.dateCreated.dateString]
                        while(cells.size() < memberNr+1)
                            cells << [v: null]
                        cells << [v:  it.mood]
                        rows << ['c': cells]
                    } else {
                        rows.get(i).c << [v: it.mood]
                    }
                }
                i++
            }
            memberNr++
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
