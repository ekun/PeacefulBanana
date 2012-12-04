package org.peaceful.banana

class NotificationController {

    static defaultAction = "center"

    def springSecurityService

    def center() {
        def user = User.get(springSecurityService.principal.id)
        def selectedNotification

        if (params.get("id")) {
            selectedNotification = Notification.findByUserAndId(user, params.getInt("id"))
            if (selectedNotification) {
                selectedNotification.unread = false
                selectedNotification.save()
            }
        }

        [user: user, selected: selectedNotification]
    }

    def unread() {
        def user = User.get(springSecurityService.principal.id)

        [trash: Notification.findAllByUserAndCleared(user, true), user: user]
    }

    def trash() {
        def user = User.get(springSecurityService.principal.id)

        [trash: Notification.findAllByUserAndCleared(user, true), user: user]
    }
}
