package org.peaceful.banana.json

import grails.converters.JSON
import org.peaceful.banana.git.GitHubService
import org.eclipse.egit.github.core.Repository
import uk.co.desirableobjects.oauth.scribe.OauthService
import org.scribe.model.Token
import org.peaceful.banana.git.util.CommitStatistics

class GitDataController {

    def OauthService oauthService = new OauthService()
    GitHubService gitHubService

    /**
     * Controller to generate json data based on the request.
     *
     * Will be used by AJAX controllers for async data-request
     */

    def index() {
        // generates a json with impact for each user in the project.
        gitHubService = new GitHubService((Token)session[oauthService.findSessionKeyForAccessToken('github')])
        Repository repository = gitHubService.getRepository(params.getLong("id"))

        render repository as JSON
    }

    def impact() {
        // generates a json with impact for each user in the project.
        gitHubService = new GitHubService((Token)session[oauthService.findSessionKeyForAccessToken('github')])
        Repository repository = gitHubService.getRepository(params.getLong("id"))

        ArrayList<CommitStatistics> impact = gitHubService.getRepositoryImpact(repository)

        def columns = []
        columns << [label: 'User', type: 'string']
        columns << [label: 'Impact', type: 'number']

        def rows = []
        def cells
        impact.each {
            cells = []
            cells << [v: it.user] << [v: it.impact]
            rows << ['c': cells]
        }

        def table = [cols: columns, rows: rows]

        render table as JSON
    }
}
