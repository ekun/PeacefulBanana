package org.peaceful.banana.git.util;

import java.util.Date;

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
public class CommitStatistics {
    private String user;
    private String message;
    private int added;
    private int deleted;
    private Date created;

    public CommitStatistics() {
        added = 0;
        deleted = 0;
    }

    public CommitStatistics(int added, int deleted) {
        this.added = added;
        this.deleted = deleted;
    }

    public CommitStatistics(int added, int deleted, String user) {
        this.added = added;
        this.deleted = deleted;
        this.user = user;
    }

    public CommitStatistics(int added, int deleted, String user, String message) {
        this.added = added;
        this.deleted = deleted;
        this.user = user;
        this.message = message;
    }

    public CommitStatistics(int added, int deleted, String user, String message, Date created) {
        this.added = added;
        this.deleted = deleted;
        this.user = user;
        this.message = message;
        this.created = created;
    }

    public int getImpact() {
        return (added + deleted);
    }

    public void setAdded(int added) {
        this.added = added;
    }

    public int getAdded() {
        return added;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public int getDeleted() {
        return deleted;
    }

    public String getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void addImpact(int added, int deleted) {
        this.added += added;
        this.deleted += deleted;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
