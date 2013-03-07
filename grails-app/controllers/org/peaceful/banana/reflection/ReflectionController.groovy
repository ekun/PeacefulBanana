package org.peaceful.banana.reflection

import org.peaceful.banana.User
import org.peaceful.banana.gitdata.Commit
import org.peaceful.banana.reflection.Note

class ReflectionController {

    def springSecurityService
    static allowedMethods = [summary: ['POST','GET']]

    def index() {
        def user = User.get(springSecurityService.principal.id)

        def notes
        switch (params.get("id")){
            case "shared":
                notes = Note.findAllByUserInListAndShared(user.activeTeam().getMembers(), true, params)
                break;
            default:
                notes = Note.findAllByUser(user, params)
                break;
        }

        [notes: notes, notesCount: Note.countByUser(user)]
    }

    def inspect() {
        def user = User.get(springSecurityService.principal.id)

        def note = Note.findById(params.id)

        if (note.user == user) {
            [note: note]
        } else {
            [note: null]
        }
    }

    def summary() {
        def user = User.get(springSecurityService.principal.id)
        def note

        if (params.get("moodSelector") || params.get("contributions") || params.get("improvements")) {
            // post data is set, now handle it
            note = new Note(mood: params.getInt("moodSelector"), contributions: params.get("contributions"), improvements: params.get("improvements"), user: user, team: user.activeTeam())
            note.save() // Validating
        }

        def teamTags = new HashMap<String, Integer>()

        // generate summary
        def commits = Commit.findAllByLoginAndCreatedAtGreaterThanEquals(user.gitLogin, new Date(System.currentTimeMillis()-86400000))
        if(commits.size() > 0) {
            commits.each {
                it.message.split(" ").each {
                    if(it.startsWith("#")) {
                        if(teamTags.get(it.toLowerCase())){
                            teamTags.putAt(it.toLowerCase(), teamTags.get(it.toLowerCase()).intValue()+1)
                        } else {
                            teamTags.put(it.toLowerCase(),1)
                        }
                    }
                }
            }
        }

        [tagCloud: teamTags, user: user, submittedForm: Note.findAllByUserAndTeamAndDateCreatedGreaterThanEquals(user,
                user.activeTeam(),
                new Date(System.currentTimeMillis()).clearTime()).size() > 0, note: note]
    }

    def mood() {
        def user = User.get(springSecurityService.principal.id)

        // retrieve the users mood-data.
    }

/**
 * This will show the user every note created by him this last periode so that he can prepair for the workshop
 */
    def preparation() {
        // get the logged in user
        def user = User.get(springSecurityService.principal.id)

        // get the notes he have created and list them
        def loggedInUserNotes

        if (params.getLong("workshop") != null) {
            def work = Workshop.findById(params.getLong("workshop"))
            loggedInUserNotes = Note.findByUserAndTeamAndDateCreatedBetween(user, work.team, work.dateStart, work.dateEnd)
        } else {
            loggedInUserNotes = user.getNotes()
        }

        [notes: loggedInUserNotes, user: user, workshops: Workshop.findAllByTeam(user.activeTeam())]
    }

    def ajaxShareNote() {
        def user = User.get(springSecurityService.principal.id)
        def note = Note.findByUserAndId(user, params.getLong("id"))
        if (note) {
            note.shared = true
            note.save()

            render "<div class='alert alert-success'>The note has been shared.</div>"
        } else {
            response.status = 500
            render "<div class='alert alert-error'>Failed to share the note.</div>"
        }
    }

    def ajaxGetNoteList() {
        def user = User.get(springSecurityService.principal.id) // get the user logged in from session

        render(template: 'listNote', model: [notes: Note.findAllByUser(user,params)])
    }
}
