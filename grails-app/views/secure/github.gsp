<html>
<head>
    <meta name="layout" content="main"/>
    <title>Admin - Github</title>
    <r:require modules="bootstrap"/>
</head>
<body>
<div class="row-fluid">
    <div class="span3">
        <div class="well sidebar-nav">
            <ul class="nav nav-list">
                <li class="nav-header">Administration</li>
                <li><a href="${createLink(action: 'index')}">Dashboard</a></li>
                <li><a href="${createLink(action: 'users')}">Users</a></li>
                <li><a href="${createLink(action: 'notes')}">Notes</a></li>
                <li class="active"><a href="${createLink(action: 'github')}">Github</a></li>
            </ul>
        </div><!--/.well -->
    </div><!--/span-->
    <div class="span9">
        <h3>Github</h3>
    </div><!--/span-->
</div><!--/row-->
</body>
</html>