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
    <table class="table table-striped">
        <thead>
        <tr>
            <g:sortableColumn property="title" title="Subject" />
            <g:sortableColumn property="dateCreated" title="Received" />
            <td class="span1"></td>
        </tr>
        </thead>
        <tbody>
        <div id="wookie">
            <g:formatNotificationList notifications="${trash}"/>
        </div>
        </tbody>
    </table>
    </div><!--/span-->
</div><!--/row-->
</body>
</html>