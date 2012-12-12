package org.peaceful.banana

class Team {

    String name
    long repository

    static constraints = {
        name blank:  false
        repository nullable: true, blank: true
    }

    List<User> getMembers() {
        TeamUser.findAllByTeam(this).user
    }
}
