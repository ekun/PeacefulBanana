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
                    dateReflectionPeriodeStart: params.getDate("dateReflectionPeriode")).save(flush: true)

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
            def maxTagCount = commitTags.values().sort()[0]

            new WorkshopQuestion(questionText: "What were your initial expectations to this iteration? Did these expectations change during the iteration? How? Why?",
                    workshop: newWorkshop).save()
            new WorkshopQuestion(questionText: "What could be done to improve team collaboration?",
                    workshop: newWorkshop).save()
            new WorkshopQuestion(questionText: "What did you do that seemed to be effective or ineffective in the team?",
                    workshop: newWorkshop).save()
            new WorkshopQuestion(questionText: "What are the most difficult or satisfying parts of your work? Why?",
                    workshop: newWorkshop).save()
            new WorkshopQuestion(questionText: "Talk about any disappointments or successes of your project. What did you learn from it?",
                    workshop: newWorkshop).save()

            commitTags.each {
                if (!(it.value > (maxTagCount/3)))
                    new WorkshopQuestion(questionText: generateQuestion(it.key, it.value, maxTagCount), workshop: newWorkshop).save()
            }

        } else {
            response.status = 500
        }
    }

    private String generateQuestion(String tag, int count, int maxCount) {
        tag = tag.substring(1)
        if (tag.integer)
            tag = Issue.findByNumber(tag.toInteger()).title

        if (count > ((maxCount*2)/3)) {
            return "You have had a high activity working with '"+tag+"'. Did you experience any particular problems? Why or why not?"
        } else if (count < ((maxCount*2)/3) && count > (maxCount/3)) {
            return "Could there be any improvements on how you worked with '"+tag+"'?"
        }
    }

}
