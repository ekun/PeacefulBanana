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
    int milestoneNumber

    static belongsTo = [repository: Repository]

    static mapping = {
        id column: 'githubId'
        body type: "text"
    }

    static constraints = {
        githubId blank: false, unique: true
        closed nullable: true
        milestoneNumber nullable: true
    }

    List<IssueEvent> getEvents() {
        IssueEvent.findAllByIssue(this, [sort:'created', order:'asc']) as List
    }

    List<IssueComment> getComments() {
        IssueComment.findAllByIssue(this, [sort:'createdAt', order:'asc']) as List
    }
}
