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
<%@ page import="org.peaceful.banana.User; org.joda.time.DateTime" %>
<g:each in="${notes}">
    <tr>
        <td>
            <a href="${createLink(action: 'inspect', id: it?.id)}">${it?.id}</a>
        </td>
        <td>
            <joda:time value="${new DateTime(it.dateCreated)}">
                <joda:format value="${it}" pattern="yyyy-MM-dd HH:mm:ss" />
            </joda:time>
        </td>
        <td>
            ${it.user == User.findByUsername(sec.loggedInUserInfo(field:'username')) ? 'You' : it.user.firstName + " " + it.user.lastName}
        </td>
        <td>
            ${it?.team?.name}
        </td>
        <td>
            <div class="pull-right">
                <g:if test="${!it.shared}">
                    <g:submitToRemote class="btn btn-primary btn-mini" action="ajaxShareNote" id="${it?.id}"
                    update="feedback"
                    value="Share" onComplete="reloadList();"/>
                </g:if>
                <g:if test="${it.shared}">
                    <button class="btn btn-success btn-mini disabled" disabled="disabled">Shared</button>
                </g:if>
            </div>
        </td>
    </tr>
</g:each>