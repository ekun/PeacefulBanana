package org.peaceful.banana.reflection

import org.peaceful.banana.TeamRole
import org.peaceful.banana.User
import org.peaceful.banana.gitdata.Commit
import org.peaceful.banana.gitdata.Issue
import org.peaceful.banana.gitdata.Milestone
import org.peaceful.banana.gitdata.Repository

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
            def workshop = Workshop.findByDateEndBetweenAndTeam(new Date(0), new Date().plus(1), user.activeTeam())

            // TODO: suggest timeslots for workshops after closed milestone perhaps?

            [team: user.activeTeam(), latestWorkshop: workshop, user: user]
        } else {
            redirect(controller: 'workshop', action: 'index')
        }
    }

    def inspect() {
        def user = User.get(springSecurityService.principal.id)

        def workshop = Workshop.findById(params.getLong("id"))

        // Check if the user is a manager / owner of his active team
        if (workshop.team == user.activeTeam() &&
                (user.teamRole() == TeamRole.MANAGER || user.activeTeam().owner == user)) {

            // Generate questions based on hashtags
            def commits = Commit.findAllByRepositoryAndCreatedAtBetween(
                    Repository.findByGithubId(user.activeTeam().repository),
                    workshop.dateStart, workshop.dateEnd)

            def commitTags = generateTagMap(commits)

            workshop.questions.each {
                commitTags.remove(it.commitTag)
            }

            [questions: workshop.questions, user: user, tags: commitTags]
        } else {
            redirect(controller: 'workshop', action: 'index')
        }
    }

    /**
     * This will render the questionare in a PDF.
     * @return pdf file containing questions.
     */
    def export() {
        def user = User.get(springSecurityService.principal.id)
        def workshop = Workshop.findById(params.getLong("id"))

        if (workshop.team == user.activeTeam() &&
                (user.teamRole() == TeamRole.MANAGER || user.activeTeam().owner == user)) {
            renderPdf(template: "workshopQuestionare", model:[workshop: workshop, questions: workshop.questions],
                    controller: 'workshop', filename: 'WorkshopQuestionare')
        } else {
            redirect(controller: 'workshop', action: 'index')
        }
    }

    def ajaxCreateWorkshop() {
        // get the logged in user
        def user = User.get(springSecurityService.principal.id)

        // Check if the user is a manager / owner of his active team
        if (user.teamRole() == TeamRole.MANAGER || user.activeTeam().owner == user) {

            def newWorkshop = new Workshop(
                    team: user.activeTeam(),
                    dateStart: (Date)params.dateReflectionPeriodeStart,
                    dateEnd: (Date)params.dateReflectionPeriodeEnd
            ).save(flush: true, failOnError: true)

            // Generate questions based on hashtags
            def commits = Commit.findAllByRepositoryAndCreatedAtBetween(
                    Repository.findByGithubId(user.activeTeam().repository),
                    (Date)params.dateReflectionPeriodeStart, (Date)params.dateReflectionPeriodeEnd)

            def commitTags = generateTagMap(commits)

            // sort after value
            def maxTagCount = commitTags.values().max()

            new WorkshopQuestion(questionText: "What were your initial expectations to this iteration? Did these expectations change during the iteration? How? Why?",
                    commitTag: "MANDATORY",
                    workshop: newWorkshop).save()
            new WorkshopQuestion(questionText: "What could be done to improve team collaboration?", commitTag: "MANDATORY",
                    workshop: newWorkshop).save()
            new WorkshopQuestion(questionText: "What did you do that seemed to be effective or ineffective in the team?",
                    commitTag: "MANDATORY",
                    workshop: newWorkshop).save()
            new WorkshopQuestion(questionText: "What are the most difficult or satisfying parts of your work? Why?",
                    commitTag: "MANDATORY",
                    workshop: newWorkshop).save()
            new WorkshopQuestion(questionText: "Talk about any disappointments or successes of your project. What did you learn from it?",
                    commitTag: "MANDATORY",
                    workshop: newWorkshop).save()


            Milestone.findByRepositoryAndState(Repository.findByGithubId(user.activeTeam().repository), "closed")
                    .each { milestone ->
                if (milestone.closed.count { it.closed?.after(milestone?.dueOn) }.intValue() > 0) {
                    // TODO: SKRIVE BEDRE SPØRSMÅL
                    new WorkshopQuestion(questionText: "Why did the team not meet the deadline on '" + milestone.title + "'?",
                            commitTag: "MANDATORY",
                            workshop: newWorkshop).save()

                    new WorkshopQuestion(questionText: "Was there anything that the differently in order to meet the deadline on '" + milestone.title + "'?",
                            commitTag: "MANDATORY",
                            workshop: newWorkshop).save()
                }
            }

            commitTags.each {
                if (!(it.value < (maxTagCount/3)))
                    new WorkshopQuestion(questionText: generateQuestion(it.key, it.value, maxTagCount),
                            commitTag: it.key, workshop: newWorkshop).save()
            }
            render "<div class='alert alert-success'>Workshop created..<br>Inspect it <a href='"+createLink(action: 'inspect', id: newWorkshop.id)+"'>here</a>.</div>"
        } else {
            log.error "User does not have the rights!"
            response.status = 500
            render "<div class='alert alert-error'>You do not have the rights.</div>"
        }

    }

    def ajaxDeleteQuestion() {
        def user = User.get(springSecurityService.principal.id)
        def workshopQuestions = WorkshopQuestion.findById(params.getLong("id"))

        // Check if the user is a manager / owner of his active team
        if (workshopQuestions.workshop.team == user.activeTeam() &&
                (user.teamRole() == TeamRole.MANAGER || user.activeTeam().owner == user)) {
            workshopQuestions.delete()
            render("SUCCESS")
        }
    }

    def ajaxGetQuestions() {
        def user = User.get(springSecurityService.principal.id)
        def workshop = Workshop.findById(params.getLong("id"))

        // Check if the user is a manager / owner of his active team
        if (workshop.team == user.activeTeam() &&
                (user.teamRole() == TeamRole.MANAGER || user.activeTeam().owner == user)) {

            // Generate questions based on hashtags
            def commits = Commit.findAllByRepositoryAndCreatedAtBetween(
                    Repository.findByGithubId(user.activeTeam().repository),
                    workshop.dateStart, workshop.dateEnd)

            def commitTags = generateTagMap(commits)

            workshop.questions.each {
                commitTags.remove(it.commitTag)
            }

            render(template: "listQuestions", model: [questions: workshop.questions, tags: commitTags])
        } else {
            render("FAIL!")
        }
    }

    def ajaxAddQuestion() {
        def user = User.get(springSecurityService.principal.id)
        def workshop = Workshop.findById(params.getLong("id"))

        if (workshop.team == user.activeTeam() &&
                (user.teamRole() == TeamRole.MANAGER || user.activeTeam().owner == user)) {

            Random randy = new Random(System.currentTimeMillis())

            new WorkshopQuestion(questionText: generateQuestion(params.get("tag"), randy.nextInt(3), 4),
                    commitTag: params.get("tag"), workshop: workshop).save()
            render("SUCCESS")
        } else {
            render("FAIL!")
        }
    }

    private String generateQuestion(String tag, int count, int maxCount) {
        if (!tag.startsWith("the "))
            tag = "the tag " + tag

        if (count > ((maxCount*2)/3)) {
            return "You have had a high activity working with "+tag+". Did you experience any particular problems? Why or why not?"
        } else if (count < ((maxCount*2)/3) && count > (maxCount/3)) {
            return "Could there be any improvements on how you worked with "+tag+"?"
        } else {
            return "What did you learn from working with "+tag+"?"
        }
    }

    private HashMap<String, Integer> generateTagMap(commits) {
        def commitTags = new HashMap<String, Integer>()

        // Gather hashtags from commit-messages
        // From a 2-3 week periode
        commits.each {
            it.message.split(" ").each {
                if(it.startsWith("#")) {
                    while(it.contains(".") || it.contains(",") || it.contains("!"))
                        it = it - "." - "!" - ","

                    def tag = it.substring(1)
                    if (tag.integer) {
                        tag = "The issue '"+Issue.findByNumber(tag.toInteger()).title+"' (#"+tag+")"
                        it = tag
                    }
                    if(commitTags.get(it.toLowerCase())){
                        commitTags.putAt(it.toLowerCase(), commitTags.get(it.toLowerCase()).intValue()+1)
                    } else {
                        commitTags.put(it.toLowerCase(),1)
                    }
                }
            }
        }
        return commitTags
    }

}
