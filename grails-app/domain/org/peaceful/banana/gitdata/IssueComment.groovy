package org.peaceful.banana.gitdata

class IssueComment {

    long githubId
    Date createdAt
    Date updatedAt
    String login
    String body

    static belongsTo = [issue: Issue]

    static mapping = {
        body type: "text"
    }

    static constraints = {
        githubId unique: true
    }
}
