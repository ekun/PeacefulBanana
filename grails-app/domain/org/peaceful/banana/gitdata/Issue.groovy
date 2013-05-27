package org.peaceful.banana.gitdata

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

class Issue {

    String title
    String body
    int number
    long githubId
    String state
    Date closed
    Date created
    Date updated
    int milestoneNumber

    static belongsTo = [repository: Repository]

    static mapping = {
        id column: 'githubId'
        body type: "text"
    }

    static constraints = {
        githubId blank: false, unique: true
        closed nullable: true
        body nullable: true
        milestoneNumber nullable: true
    }

    List<IssueEvent> getEvents() {
        IssueEvent.findAllByIssue(this, [sort:'created', order:'asc']) as List
    }

    List<IssueComment> getComments() {
        IssueComment.findAllByIssue(this, [sort:'createdAt', order:'asc']) as List
    }

    List<Commit> getCommits() {
        def list = new ArrayList<Commit>()
        Commit.findAllByRepository(repository,[sort: 'createdAt', order: 'asc']).each {
            if(it.getIssues().contains(number))
                list.add(it)
        }
        return list
    }
}
