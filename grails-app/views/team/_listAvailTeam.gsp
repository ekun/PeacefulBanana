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