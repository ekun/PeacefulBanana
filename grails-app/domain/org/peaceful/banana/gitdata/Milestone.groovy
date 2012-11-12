package org.peaceful.banana.gitdata

class Milestone implements Serializable {

    int number
    Date created
    Date dueOn
    String title
    String description
    String state
    String creator


    static belongsTo = [repository: Repository]

    static mapping = {
        id composite: ['repository', 'number']
    }

    static constraints = {
        dueOn nullable: true
        id unique: true
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
