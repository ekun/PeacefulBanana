package org.peaceful.banana.gitdata

class IssueEvent {

    String event
    Date created
    String login
    long githubId

    static belongsTo = [issue: Issue]

    static mapping = {
    }

    static constraints = {
        githubId unique: true
    }
}
