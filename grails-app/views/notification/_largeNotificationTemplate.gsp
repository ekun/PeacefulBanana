<div id="notification-${notification?.id}" class='notification' xmlns="http://www.w3.org/1999/html">
    <h3><span>${notification?.unread ? '<i class="icon-envelope" style="position:relative; top: 7px;"></i>' : ''} <a href="${createLink(controller: "notification", action: "center", id: notification?.id)}">${notification?.title}</a></span>
        <div class='pull-right'>
            <!-- Indicators to clear / markAsUnread or something -->
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
