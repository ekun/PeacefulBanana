package org.peaceful.banana

class GithubResponseController {

    /**
     * Will redirect you back to the page you where trying to access when oauth token was retrieved.
     */
    def index() {
        // Get redirect location
        log.error session.getAttribute("redirect")
        if (session.getAttribute("redirect")) {
            log.error "Redirecting!"
            def url = ((String)session.getAttribute("redirect")).split("/")
            redirect(controller: url[2], action: url[3], params: params)
        } else {
            log.error "No redirect set in session. Sending you home."
            redirect(controller: '', params: params)
        }
    }
}
