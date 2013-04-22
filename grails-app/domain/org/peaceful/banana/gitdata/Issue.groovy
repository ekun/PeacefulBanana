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
        body nullable: true
        milestoneNumber nullable: true
    }

    List<IssueEvent> getEvents() {
        IssueEvent.findAllByIssue(this, [sort:'created', order:'asc']) as List
    }

    List<IssueComment> getComments() {
        IssueComment.findAllByIssue(this, [sort:'createdAt', order:'asc']) as List
    }

    List<Commit> getCommits() {
        def list = new ArrayList<Commit>()
        Commit.findAllByRepository(repository,[sort: 'createdAt', order: 'asc']).each {
            if(it.getIssues().contains(number))
                list.add(it)
        }
        return list
    }
}
