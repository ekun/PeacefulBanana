package org.peaceful.banana.gitdata

import org.joda.time.Period
import org.joda.time.Duration

class Commit {

    String login
    String message
    Date createdAt
    int additions
    int deletions
    int total

    static belongsTo = [repository: Repository]

    static constraints = {
    }

    Period getPeriod() {
        new Period(new Duration(this.createdAt.time,System.currentTimeMillis())).normalizedStandard()
    }
}
