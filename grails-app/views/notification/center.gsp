<html>
<head>
    <meta name="layout" content="main"/>
    <title>Notification Center</title>
    <r:require modules="bootstrap"/>
</head>
<body>
<div class="row-fluid">
    <div class="span3">
        <div class="well sidebar-nav">
            <ul class="nav nav-list">
                <li class="nav-header">Notification Center</li>
                <li class="active"><a href="${createLink(action: 'center')}">Inbox</a></li>
                <li><a href="${createLink(action: 'unread')}">Unread</a></li>
                <li><a href="${createLink(action: 'trash')}">Trash</a></li>
            </ul>
        </div><!--/.well -->
    </div><!--/span-->
    <div class="span9">
        <h3>Inbox</h3>
        <g:each in="${!params.id ? user.getNotifications() : selected}">
            <g:formatNotificationLarge notification="${it}" />
        </g:each>
        ${params.id && !selected ? '<div class="alert alert-error">\n' +
                '<strong>Error</strong> Invalid id.' +
                '</div>' : ''}
    </div><!--/span-->
</div><!--/row-->
</body>
</html>