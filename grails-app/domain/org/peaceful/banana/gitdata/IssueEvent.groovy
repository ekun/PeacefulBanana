package org.peaceful.banana.gitdata

import org.joda.time.Period
import org.joda.time.Duration

class IssueEvent {

    String event
    Date created
    String login
    String githubId

    static belongsTo = [issue: Issue]

    static mapping = {
    }

    static constraints = {
        githubId unique: true
    }

    Period getPeriod() {
        new Period(new Duration(this.created.time,System.currentTimeMillis())).normalizedStandard()
    }
}
