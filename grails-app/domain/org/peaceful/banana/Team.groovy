package org.peaceful.banana

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
}
