<div id="notification-${notification?.id}" class='notification' xmlns="http://www.w3.org/1999/html">
    <h3><span>${notification?.unread ? '<i class="icon-envelope" style="position:relative; top: 7px;"></i>' : ''} <a href="${createLink(controller: "notification", action: "center", id: notification?.id)}">${notification?.title}</a></span>
        <div class='pull-right'>
            <!-- Indicators to clear / markAsUnread or something -->
            <g:submitToRemote class="btn btn-danger" action="ajaxTrash" id="${notification?.id}"
                              update="feedback"
                              value="Trash" />
        </div>
    </h3>
    <p>${notification?.body}</p>
</div>
