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
                <li class="nav-header">Menu</li>
                <li class="active"><a href="${createLink(action: 'center')}">Notification Center</a></li>
                <li><a href="${createLink(action: 'unread')}">Unread</a></li>
            </ul>
        </div><!--/.well -->
    </div><!--/span-->
    <div class="span9">
        <g:each in="${user.getNotifications()}">
            ${it.title}
        </g:each>
    </div><!--/span-->
</div><!--/row-->
</body>
</html>