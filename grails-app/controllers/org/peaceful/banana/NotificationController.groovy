package org.peaceful.banana

import grails.validation.ValidationException
import grails.converters.JSON

class NotificationController {

    static defaultAction = "center"

    def springSecurityService

    def center() {
        def user = springSecurityService.currentUser
        def selectedNotification

        [notifications: Notification.findAllByUserAndCleared(user,false,params),
                notificationsCount: Notification.countByUserAndCleared(user, false),
                user: user, selected: selectedNotification]
    }

    def inspect() {
        def user = springSecurityService.currentUser
        def selectedNotification

        if (params.get("id")) {
            selectedNotification = Notification.findByUserAndId(user, params.getInt("id"))
            if (selectedNotification && selectedNotification.unread) {
                selectedNotification.unread = false
                selectedNotification.save()
            }
        }

        [user: user, selected: selectedNotification]
    }

    def unread() {
        def user = springSecurityService.currentUser

        [unread: Notification.findAllByUserAndCleared(user, true), user: user]
    }

    def archive() {
        def user = springSecurityService.currentUser
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
        def user = springSecurityService.currentUser // get the user logged in from session
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
            render "<div class='alert alert-error'>Failed to archive the notification.</div>"
        }
        render "<div class='alert alert-success'>The notification has been archived.</div>"
    }

    def ajaxGetNotificationList() {
        def user = springSecurityService.currentUser // get the user logged in from session

        render(template: 'listNotification', model: [notifications: Notification.findAllByUserAndCleared(user,false,params)])
    }
}
