package org.peaceful.banana.reflection

class WorkshopQuestion {


    String questionType
    String questionText
    String[] questionAlternatives

    static belongsTo = [workshop: Workshop]

    static constraints = {

    }
}
