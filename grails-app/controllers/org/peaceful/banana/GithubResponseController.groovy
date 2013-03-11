package org.peaceful.banana

class GithubResponseController {

    /**
     * Will redirect you back to the page you where trying to access when oauth token was retrieved.
     */
    def index() {
        // Get redirect location
        if (session.getAttribute("redirect")) {

            def url = ((String)session.getAttribute("redirect")).split("/")

            redirect(controller: url[(url.length-2)], action: url[(url.length-1)], params: params)
        } else {
            redirect(controller: '', params: params)
        }
    }
}
