<div class='item'>
    <h3><span>${notification?.unread ? '<i class="icon-envelope" style="position:relative; top: 7px;"></i> '+notification?.title : notification?.title}</span>
        <div class='pull-right'>
            <!-- Indicators to clear / markAsUnread or something -->
            <a href="#" title="Delete"><i class="icon-trash"></i></a>
        </div>
    </h3>
    <p>${notification?.body}</p>
</div>
