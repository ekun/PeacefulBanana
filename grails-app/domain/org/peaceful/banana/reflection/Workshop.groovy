package org.peaceful.banana.reflection

import org.peaceful.banana.Team

class Workshop {

    Date dateCreated // should be set by GORM
    Date dateStart
    String duration
    Date durationStart
    Team team

    static constraints = {

    }

    def getQuestions() {
        return WorkshopQuestion.findAllByWorkshop(this, [sort: 'id', order: 'asc'])
    }
}
