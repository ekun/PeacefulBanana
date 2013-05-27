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
<%@ page import="org.peaceful.banana.gitdata.Repository" %>
<g:each in="${teams}">
    <g:if test="${org.peaceful.banana.TeamUser.countByTeamAndUser(it, user) == 0}">
        <tr>
            <td>
                ${it.name}
            </td>
            <td>
                ${Repository.findByGithubId(it.repository)?.name}
            </td>
            <td>
                ${it.getMembers().size()}
            </td>
            <td>
                <div class="pull-right">
                    <g:submitToRemote class="btn btn-danger btn-mini" action="ajaxJoinTeam" id="${it?.id}"
                                          update="[success: 'feedback', failure: 'feedback']"
                                          value="Join team" onComplete="reloadAvailList()"/>
                </div>
            </td>
        </tr>
    </g:if>
</g:each>