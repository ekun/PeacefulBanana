package org.peaceful.banana

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
                notes = Note.findAllByUserInListAndShared(user.getActiveTeam().getMembers(), true, params)
                break;
            default:
                notes = Note.findAllByUser(user, params)
                break;
        }

        [notes: notes, notesCount: Note.countByUser(user)]
    }

    def summary() {
        def user = User.get(springSecurityService.principal.id)
        def note

        if (params.get("moodSelector") || params.get("contributions") || params.get("improvements")) {
            // post data is set, now handle it
            note = new Note(mood: params.getInt("moodSelector"), contributions: params.get("contributions"), improvements: params.get("improvements"), user: user)
            note.save() // Validating
        }

        def teamTags = new HashMap<String, Integer>()        //

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

        [tagCloud: teamTags, user: user, submittedForm: Note.findAllByUserAndDateCreatedGreaterThanEquals(user,
                new Date(System.currentTimeMillis()-86400000)).size() > 0, note: note]
    }

    def mood() {
        def user = User.get(springSecurityService.principal.id)

        // retrieve the users mood-data.
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
