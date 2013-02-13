package org.peaceful.banana.reflection

class WorkshopQuestion {

    String questionText
    String commitTag

    static belongsTo = [workshop: Workshop]

    static constraints = {
        commitTag blank: true
    }
}
