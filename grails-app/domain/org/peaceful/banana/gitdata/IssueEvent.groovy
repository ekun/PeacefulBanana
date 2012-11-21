package org.peaceful.banana.gitdata

class IssueEvent {

    String event
    Date created
    String login

    static belongsTo = [issue: Issue]

    static constraints = {
    }
}
