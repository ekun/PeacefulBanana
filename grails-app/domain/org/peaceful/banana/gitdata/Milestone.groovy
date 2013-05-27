package org.peaceful.banana.gitdata

import org.joda.time.Duration
import org.joda.time.Period
import org.joda.time.format.PeriodFormatter
import org.joda.time.format.PeriodFormatterBuilder

/**
 This file is part of Peaceful Banana.

 Peaceful Banana is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 Peaceful Banana is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with Peaceful Banana.  If not, see <http://www.gnu.org/licenses/>.
 */

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
