<g:each in="${users}">
    <tr ${!it.active ? 'class="error"' : ''}>
        <td>
            ${it.user.toString()}
        </td>
        <td>
            ${it.role}
        </td>
        <td>
            <div class="pull-right">
                <g:if test="${it.role != org.peaceful.banana.TeamRole.MANAGER && user.teamRole() == org.peaceful.banana.TeamRole.MANAGER}">
                    <g:select name="teamRole" from="${org.peaceful.banana.TeamRole}">
                    </g:select>
                </g:if>
            </div>
        </td>
    </tr>
</g:each>