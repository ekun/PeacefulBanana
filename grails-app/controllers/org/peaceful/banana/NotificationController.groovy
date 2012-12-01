package org.peaceful.banana

class NotificationController {

    static defaultAction = "center"

    def springSecurityService

    def center() {
        def user = User.get(springSecurityService.principal.id)

        [user: user]
    }

    def trash() {
        def user = User.get(springSecurityService.principal.id)

        [trash: Notification.findAllByUserAndCleared(user, true), user: user]
    }
}
