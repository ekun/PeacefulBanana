package org.peaceful.banana

class TeamUser implements Serializable {

    User user
    Team team
    TeamRole role = TeamRole.DEVELOPER

    static mapping = {
        id composite:['user','team']
    }

    static constraints = {
    }

    static TeamUser create(User user, Team team, boolean flush = false) {
        new TeamUser(user: user, team: team).save(flush: flush, insert: true)
    }
}
