package org.peaceful.banana

import org.peaceful.banana.gitdata.Commit
import org.peaceful.banana.reflection.Note
import org.hibernate.classic.ValidationFailure

class ReflectionController {

    def springSecurityService
    static allowedMethods = [summary: ['POST','GET']]

    def index() {
        def user = User.get(springSecurityService.principal.id)

        [notes: Note.findAllByUser(user,params), notesCount: Note.countByUser(user)]
    }

    def summary() {
        def user = User.get(springSecurityService.principal.id)

        if (params.get("id") == "save") {
            // post data is set, now handle it
            try {
                log.error params.getInt("moodSelector")
                new Note(mood: params.getInt("moodSelector"), contributions: params.get("contributions"), improvements: params.get("improvements"), user: user).save(flush: true, failOnError: true)
            } catch (ValidationFailure e) {
                log.error "Something went wrong!"
            }
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
                new Date(System.currentTimeMillis()-86400000)).size() > 0]
    }
}
