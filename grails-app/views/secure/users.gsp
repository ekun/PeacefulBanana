<html>
<head>
    <meta name="layout" content="main"/>
    <title>Admin - Users</title>
    <r:require modules="bootstrap"/>
</head>
<body>
<div class="row-fluid">
    <div class="span3">
        <div class="well sidebar-nav">
            <ul class="nav nav-list">
                <li class="nav-header">Administration</li>
                <li><a href="${createLink(action: 'index')}">Dashboard</a></li>
                <li class="active"><a href="${createLink(action: 'users')}">Users</a></li>
                <li><a href="${createLink(action: 'notes')}">Notes</a></li>
                <li><a href="${createLink(action: 'github')}">Github</a></li>
            </ul>
        </div><!--/.well -->
    </div><!--/span-->
    <div class="span9">
        <h3>Users</h3>
        <table class="table table-striped">
            <thead>
                <tr>
                    <g:sortableColumn property="id" title="#" />
                    <g:sortableColumn property="firstName" title="Firstname" />
                    <g:sortableColumn property="lastName" title="Lastname" />
                    <th>Active team</th>
                </tr>
            </thead>
            <tbody id="target">
                <g:each in="${users}">
                    <tr ${!it.activeTeam() ? 'class="error"' : ''}>
                        <td>
                            ${it.id}
                        </td>
                        <td>
                            ${it.firstName}
                        </td>
                        <td>
                            ${it.lastName}
                        </td>
                        <td>
                            ${!it.activeTeam() ? 'N/A' : it.activeTeam().name}
                        </td>
                    </tr>
                </g:each>
            </tbody>
        </table>
        <center><g:paginate controller="secure" max="25" action="users" total="${allUsers}"/></center>
    </div><!--/span-->
</div><!--/row-->
</body>
</html>