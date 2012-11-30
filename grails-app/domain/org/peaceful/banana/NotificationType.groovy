package org.peaceful.banana

/**
 * Created with IntelliJ IDEA.
 * User: ekun
 * Date: 30.11.12
 * Time: 10:42
 */
public enum NotificationType {
    /*
        Event types
        5-min reflection session
        Collaborativ session

     */
    COLLABORATIVE_SESSION("Collaborative session"),
    FIVE_MIN_REFLECTION("5 min reflection session"),
    OTHER("OTHER")


    String name

    public NotificationType(String name){
        this.name = name
    }
}