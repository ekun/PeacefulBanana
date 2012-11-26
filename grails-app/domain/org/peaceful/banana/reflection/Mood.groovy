package org.peaceful.banana.reflection

import org.peaceful.banana.User

class Mood {

    int moodLevel
    Date createdAt

    static belongsTo = [user: User]

    static constraints = {
        moodLevel range: 1..100
    }
}
