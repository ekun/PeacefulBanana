package org.peaceful.banana.reflection

import org.peaceful.banana.User

class WorkshopQuestionAnsweres {

    Date dateCreated // should be set by GORM
    String answereText // TODO: set to type text for SQL

    static belongsTo = [user: User, question: WorkshopQuestion]

    static constraints = {

    }
}
