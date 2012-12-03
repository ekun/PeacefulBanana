package org.peaceful.banana

import org.peaceful.banana.gitdata.Commit

class ReflectionController {

    static defaultAction = "center"

    def springSecurityService

    def center() { }

    def summary() {
        def user = User.get(springSecurityService.principal.id)
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

        [tagCloud: teamTags, user: user]
    }
}
