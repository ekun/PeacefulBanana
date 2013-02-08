package org.peaceful.banana

class NotificationJob {
    static triggers = {
        cron name: 'myTrigger', cronExpression: "0 0 14 ? * MON-FRI *"
    }

    def execute() {
        TeamUser.findAllByActive(true).each {
            if(!it.user?.getLatestReflectionNotification()?.dateCreated?.after(new Date().clearTime())) {
                new Notification(
                        user: it.user,
                        title: "Reminder: Daily reflection",
                        body: "You have not completed your daily reflection! Click here to do this now",
                        notificationType: NotificationType.REFLECTION).save()
            }
        }
    }
}
