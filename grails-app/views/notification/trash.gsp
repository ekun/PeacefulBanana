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
                <li><a href="${createLink(action: 'center')}">Inbox</a></li>
                <li><a href="${createLink(action: 'unread')}">Unread</a></li>
                <li class="active"><a href="${createLink(action: 'trash')}">Trash</a></li>
            </ul>
        </div><!--/.well -->
    </div><!--/span-->
    <div class="span9">
        <h3>Trash</h3>
        <g:if test="${params.id && trashed}">
            <div class="alert alert-success">
                <strong>Success!</strong> The notification has been put in the trash.
            </div>
        </g:if>
        <g:each in="${trash}">
            <g:formatNotificationLarge notification="${it}" />
        </g:each>
    </div><!--/span-->
</div><!--/row-->
</body>
</html>