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