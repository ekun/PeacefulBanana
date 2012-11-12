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

    static mapping = {

    }

    static constraints = {
        dueOn nullable: true
    }

    boolean equals(other) {
        if(!(other instanceof Milestone))
            return false
        other.repository == this.repository && other.number == this.number
    }

    List<Issue> getIssues() {
        Issue.findAllByMilestoneNumber(this.number) as List
    }

    List<Issue> getClosed() {
        Issue.findAllByStateAndMilestoneNumber("closed", this.number) as List
    }

    List<Issue> getOpen() {
        Issue.findAllByStateAndMilestoneNumber("open", this.number) as List
    }
}
