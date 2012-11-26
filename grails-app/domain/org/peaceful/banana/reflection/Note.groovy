package org.peaceful.banana.reflection

import org.peaceful.banana.User

class Note {

    Date createdAt
    String note
    boolean shared = false

    static belongsTo = [user: User]

    static mapping = {
        note type: 'text'
    }

    static constraints = {
    }
}
