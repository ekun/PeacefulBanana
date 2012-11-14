package org.peaceful.banana

class TemplateTagLib {

    def formatMilestone = { attrs ->
        out << "<div class=''>"
        out << "<h3>${attrs.milestone.title}</h3>"
        out << "<p></p>"
        out << "</div>"
    }

    def formatIssue = { attrs ->
        out << render(template: "issueTemplate", model: [issue: attrs.issue])
    }
}
