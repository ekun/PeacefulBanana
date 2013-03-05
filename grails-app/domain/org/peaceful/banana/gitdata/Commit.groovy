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

    static mapping = {
        message type: "text"
    }

    static constraints = {
    }

    Period getPeriod() {
        new Period(new Duration(this.createdAt.time,System.currentTimeMillis())).normalizedStandard()
    }

    List<Integer> getIssues() {
        def list = new ArrayList<Integer>()
        message.split(" ").each {
            if(it.startsWith("#")){
                try {
                    list.add(Integer.parseInt(it.substring(1)))
                } catch(NumberFormatException e) {
                    // IGNORED!!!
                }
            }
        }
        return list
    }
}
