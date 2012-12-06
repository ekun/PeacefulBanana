package org.peaceful.banana

import org.codehaus.groovy.grails.web.util.StreamCharBuffer

class TemplateTagLib {

    def formatMilestone = { attrs ->
        out << render(template: "milestoneTemplate", model: [milestone: attrs.milestone])
    }

    def formatIssue = { attrs ->
        out << render(template: "singleIssueTemplate", model: [issue: attrs.issue])
    }

    def formatIssues = { attrs ->
        out << render(template: "issueTemplate", model: [issue: attrs.issue])
    }

    /**
     * This is refered too when the notification is in the inbox and formats that message
     * will allways link to notification center
     */
    def formatNotification = { attrs ->
        out << '<li style="padding: 1px 5px;">'
        if(attrs.notification?.unread)
            out << '<a href="'+createLink(controller: 'notification', action: 'center', id: attrs.notification?.id)+
                    '" style="background-color: #e2f1fb;"><i class="icon-envelope"></i> ' // TODO: Sette link til riktig sted.
        else
            out << '<a href="'+createLink(controller: 'notification', action: 'center', id: attrs.notification?.id)+'">' // TODO: Sette link til riktig sted.
        out << '<!-- Notification -->'
        out << '<b>'+attrs.notification?.title+'</b>'
        out << '<!-- END Notification -->'
        out << '</a>'
        out << '</li>'
    }

    def formatNotificationList = { attrs ->
        out << render(template: 'listNotification', model: [notifications: attrs.notifications])
    }

    def formatNotesList= { attrs ->
        out << render(template: 'listNote', model: [notes: attrs.notes])
    }

    def formatNotificationLarge = { attrs ->
        out << render(template: "largeNotificationTemplate", model: [notification: attrs.notification])
    }

    def summaryForm = { attrs ->
        if (!attrs.completed)
            out << render(template: "summaryForm")
        else {
            out << "<hr>"
            out << "<p>You have already submitted your summary today.</p>"
        }
    }

    private String getLink(NotificationType notificationType) {
        def controller = ""
        def action = ""
        switch(notificationType){
        //TODO: add more types
            case NotificationType.REFLECTION:
                controller = "reflection"
                break;

            default:
                controller = "notification"
                action = "center"
                break;
        }

        return createLink(controller: controller, action: action)
    }
}
