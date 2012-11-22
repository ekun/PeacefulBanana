package org.peaceful.banana.gitdata

import org.joda.time.Period
import org.joda.time.Duration

class IssueComment {

    long githubId
    Date createdAt
    Date updatedAt
    String login
    String body

    static belongsTo = [issue: Issue]

    static mapping = {
        body type: "text"
    }

    static constraints = {
        githubId unique: true
    }

    Period getPeriod() {
        new Period(new Duration(this.createdAt.time,System.currentTimeMillis())).normalizedStandard()
    }
}
