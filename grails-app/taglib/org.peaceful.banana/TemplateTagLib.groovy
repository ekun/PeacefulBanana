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

    def formatNotification = { attrs ->
        out << '<li style="padding: 0 5px;">'
        if(attrs.notification?.unread)
            out << '<a href="'+getLink(attrs.notification?.notificationType)+'" style="background-color: #e2f1fb;">' // TODO: Sette link til riktig sted.
        else
            out << '<a href="'+getLink(attrs.notification?.notificationType)+'">' // TODO: Sette link til riktig sted.
        out << '<!-- Notification -->'
        out << '<p><b>'+attrs.notification?.title+'</b></p>'
        out << '<p>'+attrs.notification?.body+'</p>'
        out << '<!-- END Notification -->'
        out << '</a>'
        out << '</li>'
    }

    def formatNotificationLarge = { attrs ->
        out << render(template: "largeNotificationTemplate", model: [notification: attrs.notification])
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
