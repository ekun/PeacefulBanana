package org.peaceful.banana.gitdata

import groovy.time.TimeCategory

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

    def lastCommit() {
        def last = Commit.findByRepository(this, [sort: 'createdAt', order: 'desc', max: 1])
        if (!last)
            return new Date(0) // should return 1-1-1970 00:00:00.

        use ( TimeCategory ) {
            // This will get all commits done AFTER the last one, not including the last one second time.
            last.createdAt + 1.seconds
        }
        return last.createdAt
    }
}
