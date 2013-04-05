<%@ page import="org.joda.time.DateTime" %>
<div id="notification-${notification?.id}" class='notification' xmlns="http://www.w3.org/1999/html">
    <h3><span>${notification?.unread ? '<i class="icon-envelope" style="position:relative; top: 7px;"></i>' : ''} <a href="${createLink(controller: "notification", action: "inspect", id: selected?.id)}">${selected?.title}</a></span>
        <div class='pull-right'>
            <joda:time value="${new DateTime(selected?.dateCreated)}">
                <joda:format value="${it}" pattern="yyyy-MM-dd HH:mm:ss" />
            </joda:time>
            <g:submitToRemote class="btn btn-danger" action="ajaxTrashItem" id="${notification?.id}"
                              update="feedback"
                              value="Archive" />
        </div>
    </h3>
    <p>${notification?.body}</p>
    <g:if test="${notification?.notificationType == org.peaceful.banana.NotificationType.REFLECTION}">
        <p>Go to daily summary by clicking <a href="${createLink(controller: 'reflection', action: 'summary')}">here</a>.</p>
    </g:if>
</div>
