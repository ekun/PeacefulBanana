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
                <li class="active"><a href="${createLink(action: 'unread')}">Unread</a></li>
                <li><a href="${createLink(action: 'archive')}">Archived</a></li>
            </ul>
        </div><!--/.well -->
    </div><!--/span-->
    <div class="span9">
        <h3>Unread</h3>
        <table class="table table-striped">
            <thead>
            <tr>
                <g:sortableColumn property="title" title="Subject" />
                <g:sortableColumn property="dateCreated" title="Received" />
                <td class="span1"></td>
            </tr>
            </thead>
            <tbody>
                <g:formatNotificationList notifications="${unread}"/>
            </tbody>
        </table>
    </div><!--/span-->
</div><!--/row-->
</body>
</html>