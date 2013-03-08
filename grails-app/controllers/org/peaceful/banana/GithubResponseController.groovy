package org.peaceful.banana

class GithubResponseController {

    /**
     * Will redirect you back to the page you where trying to access when oauth token was retrieved.
     */
    def index() {
        // Get redirect location
        log.error session.getAttribute("redirect")
        if (session.getAttribute("redirect")) {
            def url = ((String)session.getAttribute("redirect")).split("/")

            if (url.length < 4)
                url << ""

            redirect(controller: url[2], action: url[3], params: params)
        } else {
            redirect(controller: '', params: params)
        }
    }
}
