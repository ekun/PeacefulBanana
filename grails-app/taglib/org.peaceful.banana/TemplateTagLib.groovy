package org.peaceful.banana

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
        def controller = ""
        def action = ""
        switch(attrs.notification?.notificationType){
            case NotificationType.REFLECTION:
                controller = "reflection"
                break;

            default:
                controller = "notification"
                action = "center"
                break;
        }

        out << '<li style="padding: 0 5px;">'
        if(attrs.notification?.unread)
            out << '<a href="'+createLink(controller: controller, action: action)+'" style="background-color: #e2f1fb;">' // TODO: Sette link til riktig sted.
        else
            out << '<a href="'+createLink(controller: controller, action: action)+'">' // TODO: Sette link til riktig sted.
        out << '<!-- Notification -->'
        out << '<p><b>'+attrs.notification?.title+'</b></p>'
        out << '<p>'+attrs.notification?.body+'</p>'
        out << '<!-- END Notification -->'
        out << '</a>'
        out << '</li>'
    }
}
