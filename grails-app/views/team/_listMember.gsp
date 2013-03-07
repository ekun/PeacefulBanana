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