package org.peaceful.banana.gitdata

class Repository implements Serializable {

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
    }

    List<Issue> getIssues() {
        Issue.findAllByRepository(this) as List
    }

    List<Milestone> getMilestones() {
        Milestone.findAllByRepository(this) as List
    }
}
