<!--

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

-->
<%@ page import="org.joda.time.Duration; org.peaceful.banana.gitdata.Milestone" %>
<div class='item ${milestone?.dueOn != null && milestone?.state != "closed" ? milestone?.dueOn?.before(new Date(System.currentTimeMillis())) ? 'striped' : 'no-striped' : ''}'>
    <div class="due-time">
        ${milestone?.formatedDueDate()}.
        ${milestone?.dueOn != null && milestone?.state == "closed" ? milestone.closed.count { it.closed?.after(milestone?.dueOn) }.intValue() > 0 ? ' <span class="label label-important">Deadline not met</span>' : ' <span class="label label-success">Deadline met</span>' : '' }
    </div>
    <h3>#${milestone?.number} - ${milestone?.title}
        <div class='pull-right'>
            ${milestone?.state == "closed" ? '<span class="label label-important">Closed</span>' : '<span class="label label-success">Open</span>'}
        </div>
    </h3>
    <div class="progress${milestone?.dueOn != null ? milestone?.dueOn?.before(new Date(System.currentTimeMillis())) && milestone?.state == "open" ? ' progress-danger' : ' progress-success' : milestone.state == "closed" ? ' progress-success' : ''}">
        <div class="bar" style="width: ${milestone.closed.size() > 0 ? ((int)(milestone.closed.size() / (milestone.open.size()+milestone.closed.size()))*100) : 0}%;"></div>
    </div>
    <p>${milestone?.description}</p>
    <br>
    <g:each in="${milestone.open}">
        <p>${it?.state == "closed" ? '<span class="label label-important">Closed</span>' : '<span class="label label-success">Open</span>'}
            <a href="${createLink(action: 'issue', id: it.githubId)}">#${it.number} - ${it.title}</a>
        </p>
    </g:each>
    <g:each in="${milestone.closed}">
        <p>${it?.state == "closed" ? '<span class="label label-important">Closed</span>' : '<span class="label label-success">Open</span>'}
            <a href="${createLink(action: 'issue', id: it.githubId)}">#${it.number} - ${it.title}</a>
        </p>
    </g:each>
</div>