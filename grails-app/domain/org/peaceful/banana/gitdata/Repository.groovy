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
        Milestone.findAllByRepository(this, [sort: "dueOn", order: "asc"]) as List
    }
}
