package org.peaceful.banana.reflection

import org.apache.commons.lang.time.DurationFormatUtils
import org.joda.time.Duration
import org.joda.time.format.PeriodFormat
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

    def inspect() {
        def user = User.get(springSecurityService.principal.id)

        def workshop = Workshop.findById(params.getLong("id"))

        // Check if the user is a manager / owner of his active team
        if (workshop.team == user.activeTeam() &&
                (user.teamRole() == TeamRole.MANAGER || user.activeTeam().owner == user)) {
            [questions: workshop.questions, user: user]
        } else {
            redirect(controller: 'workshop', action: '')
        }
    }

    def ajaxCreateWorkshop() {
        // get the logged in user
        def user = User.get(springSecurityService.principal.id)

        // Check if the user is a manager / owner of his active team
        if (user.teamRole() == TeamRole.MANAGER || user.activeTeam().owner == user) {

            log.error PeriodFormat.getDefault().print(new Duration(((Date)params.dateReflectionPeriode).time, new Date().time).toPeriod())

            def newWorkshop = new Workshop(
                    team: user.activeTeam(),
                    dateStart: new Date(),
                    duration: new Duration(((Date)params.dateReflectionPeriode).time, new Date().time).toString()).save(flush: true, failOnError: true)

            // Generate questions based on hashtags
            def commits = Commit.findAllByRepositoryAndCreatedAtBetween(
                    Repository.findByGithubId(user.activeTeam().repository),
                    (Date)params.dateReflectionPeriode, new Date())

            def commitTags = new HashMap<String, Integer>()

            // Gather hashtags from commit-messages
            // From a 2-3 week periode
            commits.each {
                it.message.split(" ").each {
                    if(it.startsWith("#")) {
                        while(it.contains(".") || it.contains(",") || it.contains("!"))
                            it = it - "." - "!" - ","
                        if(commitTags.get(it.toLowerCase())){
                            commitTags.putAt(it.toLowerCase(), commitTags.get(it.toLowerCase()).intValue()+1)
                        } else {
                            commitTags.put(it.toLowerCase(),1)
                        }
                    }
                }
            }

            // sort after value
            def maxTagCount = commitTags.values().max()

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
                if (!(it.value < (maxTagCount/3)))
                    new WorkshopQuestion(questionText: generateQuestion(it.key, it.value, maxTagCount), workshop: newWorkshop).save()
            }
            render "<div class='alert alert-success'>Workshop created..</div>"
        } else {
            log.error "Not rights!"
            response.status = 500
            render "<div class='alert alert-error'>You do not have the rights.</div>"
        }

    }

    private String generateQuestion(String tag, int count, int maxCount) {
        tag = tag.substring(1)
        if (tag.integer)
            tag = "issue "+tag+" - "+Issue.findByNumber(tag.toInteger()).title
        else
            tag = "tag '"+tag+"'"

        if (count > ((maxCount*2)/3)) {
            return "You have had a high activity working with the "+tag+". Did you experience any particular problems? Why or why not?"
        } else if (count < ((maxCount*2)/3) && count > (maxCount/3)) {
            return "Could there be any improvements on how you worked with the "+tag+"?"
        } else {
            return "What did you learn from working with the "+tag+"?"
        }
    }

}
