package org.peaceful.banana.json

import grails.converters.JSON
import org.peaceful.banana.git.GitHubService
import uk.co.desirableobjects.oauth.scribe.OauthService
import org.peaceful.banana.gitdata.Commit
import org.peaceful.banana.gitdata.Repository
import org.peaceful.banana.User

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

        Commit.findAllByRepository(Repository.findByGithubId(user.selectedRepo)).each {
            cells = []
            cells << [v: it.login] << [v: it.total]
            rows << ['c': cells]
        }

        def table = [cols: columns, rows: rows]

        render table as JSON
    }
}
