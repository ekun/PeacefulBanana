package org.peaceful.banana.reflection

import org.peaceful.banana.User

class Note {

    int mood
    String contributions
    String improvements
    Date dateCreated // should be set by GORM
    boolean shared = false

    static belongsTo = [user: User]

    static constraints = {
        mood range: 1..100
    }

    static mapping = {
        contributions type: "text"
        improvements type: "text"
    }
}




