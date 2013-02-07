package org.peaceful.banana

import org.peaceful.banana.reflection.Workshop

class Team {

    String name
    long repository
    User owner

    static constraints = {
        name blank:  false
        repository nullable: true, blank: true
        owner nullable: false, blank: false
    }

    List<User> getMembers() {
        TeamUser.findAllByTeam(this).user
    }

    def getWorkshops() {
        return Workshop.findAllByTeam(this)
    }
}
