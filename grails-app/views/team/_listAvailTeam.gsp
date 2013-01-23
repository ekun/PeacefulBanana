<%@ page import="org.peaceful.banana.gitdata.Repository" %>
<g:each in="${teams}">
    <tr>
        <td>
            ${it.name}
        </td>
        <td>
            ${Repository.findByGithubId(it.repository).name}
        </td>
        <td>
            ${it.getMembers().size()}
        </td>
        <td>
            <div class="pull-right">
                <g:submitToRemote class="btn btn-danger btn-mini" action="ajaxJoinTeam" id="${it?.id}"
                                      update="[success: 'feedback', failure: 'feedback']"
                                      value="Select team" onComplete="reloadAvailList()"/>
            </div>
        </td>
    </tr>
</g:each>