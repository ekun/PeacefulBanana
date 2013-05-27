package org.peaceful.banana.gitdata

import org.joda.time.Period
import org.joda.time.Duration

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

class Commit {

    String login
    String message
    Date createdAt
    int additions
    int deletions
    int total

    static belongsTo = [repository: Repository]

    static mapping = {
        message type: "text"
    }

    static constraints = {
    }

    Period getPeriod() {
        new Period(new Duration(this.createdAt.time,System.currentTimeMillis())).normalizedStandard()
    }

    List<Integer> getIssues() {
        def list = new ArrayList<Integer>()
        message.split(" ").each {
            if(it.startsWith("#")){
                try {
                    list.add(Integer.parseInt(it.substring(1)))
                } catch(NumberFormatException e) {
                    // IGNORED!!!
                }
            }
        }
        return list
    }
}
