package org.peaceful.banana

class Notification {

    String title
    String body
    boolean unread = true
    Date dateCreated // should be set by GORM
    boolean cleared = false
    NotificationType notificationType

    static belongsTo = [user: User]

    static constraints = {
        notificationType nullable: false
    }
}
