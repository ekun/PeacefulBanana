package org.peaceful.banana

class Team {

    String name

    static hasMany = [users: User]

    static constraints = {
        name blank:  false
    }
}
