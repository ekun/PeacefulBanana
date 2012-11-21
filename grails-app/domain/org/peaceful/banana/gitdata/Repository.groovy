package org.peaceful.banana.gitdata

class Repository {

    String name
    String description
    Date created
    Date updated
    long githubId

    static mapping = {
        id column: 'githubId'
    }

    static constraints = {
        githubId blank: false, unique: true
        name blank: false
        updated blank: false
        description type: "text"
    }

    List<Issue> getIssues() {
        Issue.findAllByRepositoryAndMilestoneNumber(this, 0) as List
    }

    List<Milestone> getMilestones() {
        def list = Milestone.findAllByRepositoryAndDueOnIsNotNullAndState(this,"open",[sort: 'dueOn', order: 'asc']) as List
        list.addAll(Milestone.findAllByRepositoryAndDueOnIsNotNullAndState(this,"closed") as List)
        list.addAll(Milestone.findAllByRepositoryAndDueOnIsNull(this) as List)
        return list
    }
}
