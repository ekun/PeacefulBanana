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
        out << '<li style="padding: 0 5px;">'
        if(attrs.notification?.unread)
            out << '<a href="#" style="background-color: #e2f1fb;">' // TODO: Sette link til riktig sted.
        else
            out << '<a href="#">' // TODO: Sette link til riktig sted.
        out << '<!-- Notification -->'
        out << '<p><b>'+attrs.notification?.title+'</b></p>'
        out << '<p>'+attrs.notification?.body+'</p>'
        out << '<!-- END Notification -->'
        out << '</a>'
        out << '</li>'
    }
}
