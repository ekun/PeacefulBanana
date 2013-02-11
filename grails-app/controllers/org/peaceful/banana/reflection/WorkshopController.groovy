package org.peaceful.banana.reflection

import org.peaceful.banana.TeamRole
import org.peaceful.banana.User
import org.peaceful.banana.gitdata.Commit
import org.peaceful.banana.gitdata.Issue
import org.peaceful.banana.gitdata.Repository

class WorkshopController {

    def springSecurityService

    /**
     * Will show the upcomming workshop(s)
     *
     * Admins will be able to create workshops
     */
    def index() {
        // get the logged in user
        def user = User.get(springSecurityService.principal.id)

        [workshops: user.activeTeam().workshops]
    }

    def create() {
        // get the logged in user
        def user = User.get(springSecurityService.principal.id)

        // Check if the user is a manager / owner of his active team
        if (user.teamRole() == TeamRole.MANAGER || user.activeTeam().owner == user) {
            [team: user.activeTeam()]
        } else {
            redirect(controller: 'workshop', action: '')
        }
    }

    def ajaxCreateWorkshop() {
        // get the logged in user
        def user = User.get(springSecurityService.principal.id)

        // Check if the user is a manager / owner of his active team
        if (user.teamRole() == TeamRole.MANAGER || user.activeTeam().owner == user) {

            def newWorkshop = new Workshop(team: user.activeTeam(), dateStart: params.getDate("dateStart"),
                    dateReflectionPeriodeStart: params.getDate("dateReflectionPeriode")).save()

            // Generate questions based on hashtags
            def commits = Commit.findAllByRepositoryAndCreatedAtBetween(
                    Repository.findByGithubId(user.activeTeam().repository),
                    params.getDate("dateReflectionPeriode"), new Date())

            def commitTags = new HashMap<String, Integer>()

            // Gather hashtags from commit-messages
            // From a 2-3 week periode
            commits.each {
                it.message.split(" ").each {
                    if(it.startsWith("#")) {
                        if(commitTags.get(it.toLowerCase())){
                            commitTags.putAt(it.toLowerCase(), commitTags.get(it.toLowerCase()).intValue()+1)
                        } else {
                            commitTags.put(it.toLowerCase(),1)
                        }
                    }
                }
            }

            // sort after value
            commitTags.sort()
            def maxTagCount = commitTags.values()[0]

            commitTags.each {
                new WorkshopQuestion(questionText: generateQuestion(it.key, it.value, maxTagCount)).save()
            }

        } else {
            response.status = 500
        }
    }

    private String generateQuestion(String tag, int count, int maxCount) {

        if (tag.substring(1).integer)
            tag = Issue.findByNumber(tag.substring(0).toInteger()).title

        if (count > ((maxCount*2)/3)) {
            return "Was there anything that could have been done differently when working on '"+tag+"'?"
        } else if (count < ((maxCount*2)/3) && count > (maxCount/3)) {

        }
        return ""
    }

}
