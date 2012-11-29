package org.peaceful.banana

class Notification {

    String title
    String body
    boolean unread = true
    Date createdAt
    boolean cleared = false

    static belongsTo = [user: User]

    static constraints = {
    }
}
