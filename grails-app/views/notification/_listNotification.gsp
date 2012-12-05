<%@ page import="org.joda.time.DateTime" %>
<g:each in="${notifications}">
    <tr ${it?.unread ? 'class=""' : ''}>
        <td>
            <a href="${createLink(action: 'center', id: it?.id)}">${it.title}</a>
        </td>
        <td>
            <joda:time value="${new DateTime(it.dateCreated)}"/>
        </td>
        <td>
            <g:submitToRemote class="btn btn-danger pull-right" action="ajaxTrash" id="${it?.id}"
                update="feedback"
                value="Trash" />
        </td>
    </tr>
</g:each>