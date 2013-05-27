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
<g:each in="${users}">
    <tr ${!it.active ? 'class="error"' : ''}>
        <td>
            ${it.user.toString()}
        </td>
        <td>
            <div class="pull-right">
                <g:if test="${(it.role != org.peaceful.banana.TeamRole.MANAGER && user.teamRole() == org.peaceful.banana.TeamRole.MANAGER) || (it.team.owner == user && it.user != user)}">
                    <g:form>
                        <div class="input-append">
                            <g:submitToRemote class="btn" action="ajaxChangeUserTeamRole" value="Go" update="feedback" onComplete="reloadList()"/>
                            <g:select name="teamRole" from="${org.peaceful.banana.TeamRole}" value="${it.role}" />
                            <g:hiddenField name="userId" value="${it.user.id}" />
                            <g:hiddenField name="teamId" value="${it.team.id}" />
                        </div>
                    </g:form>
                </g:if>
                <g:else>
                    ${it.role}
                </g:else>
            </div>
        </td>
    </tr>
</g:each>