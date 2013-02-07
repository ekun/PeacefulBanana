package org.peaceful.banana.reflection

import org.peaceful.banana.Team
import org.peaceful.banana.User

class Workshop {

    Date dateCreated // should be set by GORM
    Date dateStart
    Date dateDeadline // default 5 days.
    Team team

    static constraints = {

    }

    /**
     * True / false if the worshop has opened or not.
     *
     * @return
     */
    def isOpen() {
        // Check if a worshop has opened
        def today = new Date()

        // will return true if today is after dateStart
        return today.after(dateStart)
    }

    /**
     * Will return the amount of % the users have completed this workshop.
     *
     * @param user
     * @return
     */
    def userHasCompleted(User user) {
        // TODO: blir en helsikes sql spørring... må gi svar i prosent fullført
        def allQuestions = getQuestions()
        def answeredQuestionsByUser = WorkshopQuestionAnsweres.findAllByQuestionInListAndUser(allQuestions, user)
        return (answeredQuestionsByUser.size() / allQuestions.size())
    }

    def getQuestions() {
        return WorkshopQuestion.findAllByWorkshop(this)
    }
}
