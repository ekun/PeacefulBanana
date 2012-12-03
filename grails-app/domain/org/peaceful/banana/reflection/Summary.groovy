package org.peaceful.banana.reflection

import org.peaceful.banana.User

class Summary {

    int mood
    String contributions
    String improvements
    Date createdAt

    static belongsTo = [user: User]

    static constraints = {
        mood range: 1..100
    }
}




