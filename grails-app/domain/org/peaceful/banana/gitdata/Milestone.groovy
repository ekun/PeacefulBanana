package org.peaceful.banana.gitdata

import org.joda.time.Duration
import org.joda.time.Period
import org.joda.time.format.PeriodFormatter
import org.joda.time.format.PeriodFormatterBuilder

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
        child(sort:'dueOn', order:'asc')
        description type: "text"
    }

    static constraints = {
        dueOn nullable: true
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

    String formatedDueDate() {
        if(dueOn != null) {
            PeriodFormatter daysHoursMinutes = new PeriodFormatterBuilder()
                    .appendMonths()
                    .appendSuffix(" month", " months")
                    .appendSeparator(" and ")
                    .appendWeeks()
                    .appendSuffix(" week", " weeks")
                    .appendSeparator(" and ")
                    .appendDays()
                    .appendSuffix(" day", " days")
                    .appendSeparator(" and ")
                    .appendHours()
                    .appendSuffix(" hour", " hours")
                    .appendSeparator(" and ")
                    .appendMinutes()
                    .appendSuffix(" minute", " minutes")
                    .toFormatter();
            String duration = daysHoursMinutes.print(new Period(new Duration(System.currentTimeMillis(), this.dueOn.time)).normalizedStandard())
            if(duration.contains("-") && state == "closed")
                return "Due date was " + duration.replaceAll("-","")+ " ago" // Is overdue, handled in template
            else if(duration.contains("-") && state == "open")
                return "Due datea " + duration.replaceAll("-","")+ " ago" // Is overdue, handled in template
            return "Due in " + duration
        }
        return "No due date";
    }
}
