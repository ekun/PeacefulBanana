<%@ page import="org.joda.time.DateTime" %>
<g:each in="${notes}">
    <tr>
        <td>
            <a href="${createLink(action: 'index', id: it?.id)}">${it?.id}</a>
        </td>
        <td>
            <joda:time value="${new DateTime(it.dateCreated)}">
                <joda:format value="${it}" pattern="yyyy-MM-dd HH:mm:ss" />
            </joda:time>
        </td>
        <td>
            <div class="pull-right">
                <g:if test="${!it.shared}">
                    <g:submitToRemote class="btn btn-primary btn-mini" action="ajaxShareItem" id="${it?.id}"
                    update="feedback"
                    value="Share" onComplete="reloadList();"/>
                </g:if>
                <g:if test="${it.shared}">
                    <button class="btn btn-success btn-mini disabled" disabled="disabled">Shared</button>
                </g:if>
            </div>
        </td>
    </tr>
</g:each>