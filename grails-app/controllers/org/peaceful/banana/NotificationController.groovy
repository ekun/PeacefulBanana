package org.peaceful.banana

import grails.validation.ValidationException
import grails.converters.JSON

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

        [unread: Notification.findAllByUserAndCleared(user, true), user: user]
    }

    def trash() {
        def user = User.get(springSecurityService.principal.id)
        def trashed

        if (params.get("id")) {
            trashed = Notification.findByUserAndId(user, params.getInt("id"))
            if (trashed) {
                trashed.cleared = true
                trashed.save(flush: true)
            }
        }

        [trash: Notification.findAllByUserAndCleared(user, true), user: user, trashed: trashed]
    }

    // for ajax-calls
    def ajaxTrashItem() {
        def user = User.get(springSecurityService.principal.id) // get the user logged in from session
        def fail = false

        if (params.get("id")) {
            def trashed = Notification.findByUserAndId(user, params.getInt("id"))
            if (trashed) {
                trashed.cleared = true
                try {
                    trashed.save(flush: true, failOnError: true)
                } catch (ValidationException e) {
                    fail = true
                }
            }
        }

        if (fail) {
            response.status = 500
            render "<div class='alert alert-error'>Failed to put the notification in the trash.</div>"
        }
        render "<div class='alert alert-success'>The notification has been put in the trash.</div>"
    }

    def ajaxGetNotifications() {
        def user = User.get(springSecurityService.principal.id) // get the user logged in from session

        user.getAllNotifications() as JSON
    }

    def ajaxGetUnreadNotifications() {
        def user = User.get(springSecurityService.principal.id) // get the user logged in from session

        Notification.findAllByUserAndUnread(user, true) as JSON
    }
}
