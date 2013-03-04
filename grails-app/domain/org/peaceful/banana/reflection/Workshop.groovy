package org.peaceful.banana.reflection

import org.joda.time.Duration
import org.joda.time.format.PeriodFormatter
import org.joda.time.format.PeriodFormatterBuilder
import org.peaceful.banana.Team

class Workshop {

    Date dateCreated // should be set by GORM
    Date dateStart
    Date dateEnd
    Team team

    static constraints = {

    }

    def getQuestions() {
        return WorkshopQuestion.findAllByWorkshop(this, [sort: 'id', order: 'asc'])
    }

    def getDuration() {
        PeriodFormatter monthWeekDays = new PeriodFormatterBuilder()
                .appendMonths()
                .appendSuffix(" month", " months")
                .appendSeparator(" and ")
                .appendWeeks()
                .appendSuffix(" week", " weeks")
                .appendSeparator(" and ")
                .appendDays()
                .appendSuffix(" day", " days")
                .toFormatter();

        return monthWeekDays.print(new Duration(dateEnd.getTime() - dateStart.getTime()).toPeriod().normalizedStandard())
    }
}
