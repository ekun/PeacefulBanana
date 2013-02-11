package org.peaceful.banana.reflection

import org.peaceful.banana.Team

class Workshop {

    Date dateCreated // should be set by GORM
    Date dateStart
    Date dateReflectionPeriodeStart
    Team team

    static constraints = {

    }

    def getQuestions() {
        return WorkshopQuestion.findAllByWorkshop(this)
    }
}
