package org.peaceful.banana.gitdata

class Commit {

    String login
    String message
    int additions
    int deletions
    int total

    static belongsTo = [repository: Repository]

    static constraints = {
    }


}
