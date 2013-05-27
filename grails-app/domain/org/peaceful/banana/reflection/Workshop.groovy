package org.peaceful.banana.reflection

import org.joda.time.Duration
import org.joda.time.format.PeriodFormatter
import org.joda.time.format.PeriodFormatterBuilder
import org.peaceful.banana.Team

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
