package org.peaceful.banana.gitdata

class Issue {

    String title
    String body
    int number
    long githubId
    String state
    Date closed
    Date created
    Date updated

    static belongsTo = [repository: Repository, milestone: Milestone]

    static mapping = {
        id column: 'githubId'
    }

    static constraints = {
        githubId blank: false, unique: true
        closed nullable: true
        milestone nullable: true
    }
}
