<%@ page import="org.peaceful.banana.gitdata.Repository" %>
<g:each in="${teams}">
    <tr>
        <td>
            <a href="${createLink(action: 'inspect', id: it.id)}">${it.name}</a>
        </td>
        <td>
            ${Repository.findByGithubId(it.repository).name}
        </td>
        <td>
            ${it.getMembers().size()}
        </td>
        <td>
            <div class="pull-right">
                <g:if test="${it == user.activeTeam()}">
                    <button class="btn btn-success btn-mini" disabled="true">Active</button>
                </g:if>
                <g:else>
                    <g:submitToRemote class="btn btn-danger btn-mini" action="ajaxSwapTeam" id="${it?.id}"
                                      update="feedback"
                                      value="Select team" onComplete="reloadList()"/>
                </g:else>
            </div>
        </td>
    </tr>
</g:each>