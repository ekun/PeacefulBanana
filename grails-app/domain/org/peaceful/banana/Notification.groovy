package org.peaceful.banana

class Notification {

    String title
    String body
    boolean unread = true
    Date dateCreated
    boolean cleared = false
    String type

    static belongsTo = [user: User]

    static constraints = {
    }
}