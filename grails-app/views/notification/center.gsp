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
                <li><a href="${createLink(action: 'archive')}">Archived</a></li>
            </ul>
        </div><!--/.well -->
    </div><!--/span-->
    <div class="span9">
        <h3>Inbox</h3>
        <div id="feedback"></div>
        <g:if test="${!params.id}">
            <table class="table table-striped">
                <thead>
                <tr>
                    <g:sortableColumn property="title" title="Subject" />
                    <g:sortableColumn property="dateCreated" title="Received" />
                    <th></th>
                </tr>
                </thead>
                <tbody id="target">
                    <g:formatNotificationList notifications="${notifications}"/>
                </tbody>
            </table>
            <center><g:paginate controller="notification" maxsteps="5" action="center" total="${notificationsCount}"/></center>
        </g:if>
        <g:if test="${params.id}">
        ${params.id && !selected ? '<div class="alert alert-error">\n' +
                '<strong>Error</strong> Invalid id.' +
                '</div>' : ''}
            <g:formatNotificationLarge notification="${selected}" />
        </g:if>
    </div><!--/span-->
</div><!--/row-->
<g:javascript>
    function reloadList() {
        $.ajax({type: "POST",
            url: "${createLink(controller: 'notification', action: 'ajaxGetNotificationList', params: params)}",
            success: function(msg){
                document.getElementById('target').innerHTML = msg;
            }
        });
    }
</g:javascript>
</body>
</html>