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
<%@ page import="org.joda.time.DateTime" %>
<div id="notification-${notification?.id}" class='notification' xmlns="http://www.w3.org/1999/html">
    <h3><span>${notification?.unread ? '<i class="icon-envelope" style="position:relative; top: 7px;"></i>' : ''} <a href="${createLink(controller: "notification", action: "inspect", id: selected?.id)}">${selected?.title}</a></span>
        <div class='pull-right'>
            <joda:time value="${new DateTime(selected?.dateCreated)}">
                <joda:format value="${it}" pattern="yyyy-MM-dd HH:mm:ss" />
            </joda:time>
            <g:submitToRemote class="btn btn-danger" action="ajaxTrashItem" id="${notification?.id}"
                              update="feedback"
                              value="Archive" />
        </div>
    </h3>
    <p>${notification?.body}</p>
    <g:if test="${notification?.notificationType == org.peaceful.banana.NotificationType.REFLECTION}">
        <p>Go to daily summary by clicking <a href="${createLink(controller: 'reflection', action: 'summary')}">here</a>.</p>
    </g:if>
</div>
