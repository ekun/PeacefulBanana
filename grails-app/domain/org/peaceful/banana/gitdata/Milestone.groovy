package org.peaceful.banana.gitdata

class Milestone {

    int number
    Date created
    Date dueOn
    String title
    String description
    String state
    String creator


    static belongsTo = [repository: Repository]

    static constraints = {
        dueOn nullable: true
    }

    List<Issue> getIssues() {
        Issue.findAllByMilestone(this) as List
    }

    List<Issue> getClosed() {
        Issue.findAllByState("closed") as List
    }

    List<Issue> getOpen() {
        Issue.findAllByState("open") as List
    }
}
