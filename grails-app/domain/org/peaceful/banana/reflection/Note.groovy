package org.peaceful.banana.reflection

import org.peaceful.banana.User

class Note {

    Date createdAt
    String note

    static belongsTo = [user: User]

    static mapping = {
        note type: 'text'
    }

    static constraints = {
    }
}
