package org.peaceful.banana.reflection

class WorkshopQuestion {

    String questionText

    static belongsTo = [workshop: Workshop]

    static constraints = {

    }
}
