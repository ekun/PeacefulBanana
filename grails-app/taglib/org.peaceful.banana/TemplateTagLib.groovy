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
        if (attrs.notification.size() > 0) {
            attrs.notification.each {
                out << '<li style="padding: 1px 5px; overflow: hidden;">'
                out << '<!-- Notification -->'
                if(it.unread)
                    out << '<a href="'+createLink(controller: 'notification', action: 'inspect', id: it.id)+
                            '" style="background-color: #e2f1fb;"><i class="icon-envelope"></i> '
                else
                    out << '<a href="'+createLink(controller: 'notification', action: 'inspect', id: it.id)+'">'

                out << '<b>'+it.title+'</b>'
                out << '</a>'
                out << '<!-- END Notification -->'
                out << '</li>'
            }
        } else {
            out << '<li style="padding: 1px 5px;">'
            out << '<!-- Notification -->'
            out << '<center>Empty inbox</center>'
            out << '<!-- END Notification -->'
            out << '</li>'
        }
    }

    def formatNotificationList = { attrs ->
        out << render(template: 'listNotification', model: [notifications: attrs.notifications])
    }

    def formatNotesList= { attrs ->
        out << render(template: 'listNote', model: [notes: attrs.notes])
    }

    def formatTeamMembers = { attrs ->
        out << render(template: "listMember", model: [users: attrs.users, user: attrs.user])
    }

    def formatTeams = { attrs ->
        out << render(template: "listTeam", model: [teams: attrs.teams, user: attrs.user])
    }

    def formatAvailTeams = { attrs ->
        out << render(template: "listAvailTeam", model: [teams: attrs.teams, user: attrs.user])
    }

    def formatWorkshopQuestions = { attrs ->
        out << render(template: "listQuestions", model: [questions: attrs.questions, tags: attrs.tags])
    }

    def githubOAuth = { attrs ->
        out << render(template: "/template/gitOAuth", )
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

}
