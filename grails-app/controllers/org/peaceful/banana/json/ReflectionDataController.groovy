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

        def rows = []
        def columns = []

        def startDate = new Date(0)
        def endDate = new Date()

        if (params.dateStart && params.dateEnd) {
            startDate = params.getDate("dateStart")
            endDate = params.getDate("dateEnd").plus(1)
        }

        // Gathering the first ever note created by this team
        def firstNote = Note.findAllByTeamAndUserInListAndDateCreatedBetween(user.activeTeam(),
                teamMember,
                startDate,
                endDate,
                [sort: "dateCreated", order: "asc", max: 1]) // Get the first!

        if (firstNote) {
            teamMember.remove(firstNote[0].user)


            columns << [label: 'Date', type: 'string']
            columns << [label: firstNote[0].user.firstName + " " + firstNote[0].user.lastName, type: 'number']
            teamMember.each {
                columns << [label: it.firstName + " " + it.lastName, type: 'number']
            }

            def i = 0
            def memberNr = 1
            def cells

            // Gather mood-data with timestampss
            // From the member with the earliest note
            Note.findAllByUserAndTeamAndDateCreatedBetween(firstNote[0].user, user.activeTeam(),
                    startDate,
                    endDate,
                    [sort: "dateCreated", order:'asc']).each {
                cells = []
                cells << ['v':  it.dateCreated.dateString] << ['v': it.mood]
                rows << ['c': cells]
            }
            // From the rest
            teamMember.each {
                i = 0
                Note.findAllByUserAndTeamAndDateCreatedBetween(it, user.activeTeam(),
                        startDate,
                        endDate,
                        [sort: "dateCreated", order:'asc']).each {
                    cells = []
                    if (rows.size() <= i) {
                        if (!cells.empty)
                            cells.clear()
                        cells << ['v':  it.dateCreated.dateString]
                        while(cells.size() < memberNr+1)
                            cells << ['v': null]
                        cells << ['v':  it.mood]
                        rows << ['c': cells]
                    } else {
                        boolean inserted = false
                        while (rows.size() > i && !inserted) {
                            if (it.dateCreated.dateString == rows.get(i).c[0].v) {
                                //log.error i + "("+(memberNr+1)+") :: "+ it.dateCreated.dateString +" == " + rows.get(i).c[0].v
                                // At the correct date, now add it to the correct user!
                                while (rows.get(i).c.size() < memberNr)
                                    rows.get(i).c << [v: null]
                                rows.get(i).c << ['v': it.mood]

                                inserted = true
                            } else if (it.dateCreated.after(new Date(Date.parse(rows.get(i).c[0].v)))) {
                                //log.error i + "("+(memberNr+1)+") :: "+ it.dateCreated.dateString +" > " + rows.get(i).c[0].v
                                rows.get(i).c << [v: null]
                                i++
                            } else if (it.dateCreated.before(new Date(Date.parse(rows.get(i).c[0].v)))) {
                                //log.error i + "("+(memberNr+1)+") :: "+ it.dateCreated.dateString +" < " + rows.get(i).c[0].v
                                if (!cells.empty)
                                    cells.clear()
                                cells << ['v':  it.dateCreated.dateString]
                                while (cells.size() < memberNr+1)
                                    cells << ['v': null]
                                cells << ['v':  it.mood]

                                // Add the date with mood
                                rows.add(i, ['c': cells])

                                inserted = true
                            }
                        }
                        if (!inserted) {
                            // If not inserted while looping then insert it at the end.
                            //log.error i + "("+(memberNr+1)+") :: Inserted at the end!"
                            if (!cells.empty)
                                cells.clear()
                            cells << ['v':  it.dateCreated.dateString]
                            while (cells.size() < memberNr+1)
                                cells << ['v': null]
                            cells << ['v':  it.mood]

                            rows.add(['c': cells])
                        }
                    }
                    i++
                }
                memberNr++
            }
        }
        def table = [cols: columns, rows: rows]

        render table as JSON
    }

}
