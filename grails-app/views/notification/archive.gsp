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
                <li class="active"><a href="${createLink(action: 'archive')}">Archived</a></li>
            </ul>
        </div><!--/.well -->
    </div><!--/span-->
    <div class="span9">
        <h3>Archive</h3>
        <g:if test="${params.id && trashed}">
            <div class="alert alert-success">
                <strong>Success!</strong> The notification has been put in the archive.
            </div>
        </g:if>
    <table class="table table-striped">
        <thead>
        <tr>
            <g:sortableColumn property="title" title="Subject" />
            <g:sortableColumn property="dateCreated" title="Received" />
            <th></th>
        </tr>
        </thead>
        <tbody>
        <div id="wookie">
            <g:formatNotificationList notifications="${trash}"/>
        </div>
        </tbody>
    </table>
    </div><!--/span-->
    <g:javascript>
        function reloadList() {
            $.ajax({type: "POST",
                url: "/notification/ajaxGetNotificationList",
                success: function(msg){
                    document.getElementById('target').innerHTML = msg;
                }
            });
        }
    </g:javascript>
</div><!--/row-->
</body>
</html>