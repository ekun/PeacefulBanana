package org.peaceful.banana.json

import grails.converters.JSON
import org.peaceful.banana.gitdata.Commit
import org.peaceful.banana.gitdata.Repository
import org.peaceful.banana.User
import org.hibernate.criterion.CriteriaSpecification

class GitDataController {

    def springSecurityService

    /**
     * Controller to generate json data based on the request.
     *
     * Will be used by AJAX controllers for async data-request
     */

    def index() {
        // generates a json with impact for each user in the project.
        def repository = Repository.findByGithubId(params.getLong("id"))

        render repository as JSON
    }

    def impact() {
        // generates a json with impact for each user in the project.
        def user = User.get(springSecurityService.principal.id)

        def columns = []
        columns << [label: 'User', type: 'string']
        columns << [label: 'Impact', type: 'number']

        def rows = []
        def cells

        Commit.executeQuery("SELECT login, SUM(total) FROM Commit WHERE repository = :repository GROUP BY login",
                [repository: Repository.findByGithubId(user.selectedRepo)]).each {
            cells = []
            cells << [v: it[0]] << [v: it[1]]
            rows << ['c': cells]
        }

        def table = [cols: columns, rows: rows]

        render table as JSON
    }

    def yourimpact() {
        def user = User.get(springSecurityService.principal.id)

        def columns = []
        columns << [label: 'User', type: 'string']
        columns << [label: 'Impact', type: 'number']

        def rows = []
        def cells

        Commit.executeQuery("SELECT login, SUM(total) FROM Commit WHERE repository = :repository AND createdAt > :date GROUP BY login",
                [repository: Repository.findByGithubId(user.selectedRepo), date: new Date(System.currentTimeMillis()-86400000)]).each {
            cells = []
            if (it[0] == user.gitLogin){
                cells << [v: "YOU"] << [v: it[1]]
            } else {
                cells << [v: it[0]] << [v: it[1]]
            }
            rows << ['c': cells]
        }

        def table = [cols: columns, rows: rows]

        render table as JSON
    }
}
