package org.peaceful.banana

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

class NotificationJob {
    static triggers = {
        cron name: 'myTrigger', cronExpression: "0 0 14 ? * MON-FRI *"
    }

    def execute() {
        TeamUser.findAllByActive(true).each {
            if(!it.user?.getLatestReflectionNote()?.dateCreated?.after(new Date().clearTime())) {
                new Notification(
                        user: it.user,
                        title: "Reminder: Daily reflection",
                        body: "You have not completed your daily reflection for "+new Date().format("EEE, d MMM yyyy")+"!" +
                                "These notes are registrated on the date they where taken and it is not possible to create notes for dates already passed.<br>" +
                                "If that is the case for this you can ignore this notification.<br>" +
                                "<br>" +
                                "Click here to do this now",
                        notificationType: NotificationType.REFLECTION).save()
            }
        }
    }
}
