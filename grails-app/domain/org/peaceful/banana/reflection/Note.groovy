package org.peaceful.banana.reflection

import org.peaceful.banana.User
import grails.validation.Validateable

@Validateable
class Note {

    int mood
    String contributions
    String improvements
    Date dateCreated // should be set by GORM
    boolean shared = false

    static belongsTo = [user: User]

    static constraints = {
        mood range: 1..100, nullable: false
        contributions blank: false, nullable: false
        improvements blank: false, nullable: false
    }

    static mapping = {
        contributions type: "text"
        improvements type: "text"
    }
}




