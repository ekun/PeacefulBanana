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
<g:each in="${notifications}">
    <tr ${it?.unread ? 'class=""' : ''}>
        <td>
            <a href="${createLink(action: 'inspect', id: it?.id)}">${it?.unread ? '<i class="icon-envelope"></i>' : ''} ${it?.title}</a>
        </td>
        <td>
            <joda:time value="${new DateTime(it.dateCreated)}">
                <joda:format value="${it}" pattern="yyyy-MM-dd HH:mm:ss" />
            </joda:time>
        </td>
        <td>
            <div class="pull-right">
                <g:submitToRemote class="btn btn-danger btn-mini" action="ajaxTrashItem" id="${it?.id}"
                update="feedback"
                value="Archive" onComplete="reloadList();"/>
            </div>
        </td>
    </tr>
</g:each>