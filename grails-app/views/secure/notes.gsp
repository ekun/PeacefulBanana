<html>
<head>
    <meta name="layout" content="main"/>
    <title>Admin - Notes</title>
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
                <li><a href="${createLink(action: 'notes')}">Reflection</a></li>
                <ul class="nav nav-list">
                    <li ${!params.get("id") ? 'class="active"' : ''}><a href="${createLink(action: 'notes')}">Notes</a></li>
                    <li ${params.get("id") == "stats" ? 'class="active"' : ''}><a href="${createLink(action: 'notes', id: 'stats')}">Statistics</a></li>
                    <li ${params.get("id") == "mood" ? 'class="active"' : ''}><a href="${createLink(action: 'notes', id: 'mood')}">Mood</a></li>
                </ul>
                <li><a href="${createLink(action: 'github')}">Github</a></li>
            </ul>
        </div><!--/.well -->
    </div><!--/span-->
    <div class="span9">
        <g:if test="${params.get("id") == "stats"}">
            <h3>Statistics</h3>
            <table class="table table-striped">
                <thead>
                <tr>
                    <g:sortableColumn property="id" title="#" />
                    <g:sortableColumn property="dateCreated" title="Created" />
                    <g:sortableColumn property="user" title="User" />
                    <g:sortableColumn property="team" title="Team" />
                    <g:sortableColumn property="shared" title="Team" />
                </tr>
                </thead>
                <tbody>
                <g:each in="${notes}">
                    <tr>
                        <td>
                            ${it.id}
                        </td>
                        <td>
                            ${it.dateCreated}
                        </td>
                        <td>
                            ${it.user.toString()}
                        </td>
                        <td>
                            ${it.team.name}
                        </td>
                        <td>
                            ${!it.shared ? 'Shared' : 'Private'}
                        </td>
                    </tr>
                </g:each>
                </tbody>
            </table>
            <center><g:paginate controller="secure" maxsteps="5" action="notes" total="${allNotes}"/></center>
        </g:if>
        <g:elseif test="${params.get("id") == "mood"}">
            <h3>Mood</h3>
            <p>Here you can see the mood of the different teams.</p>
        </g:elseif>
        <g:else>
            <h3>Notes</h3>
            <table class="table table-striped">
                <thead>
                <tr>
                    <g:sortableColumn property="id" title="#" />
                    <g:sortableColumn property="dateCreated" title="Created" />
                    <g:sortableColumn property="user" title="User" />
                    <g:sortableColumn property="team" title="Team" />
                    <g:sortableColumn property="shared" title="Team" />
                </tr>
                </thead>
                <tbody id="target">
                <g:each in="${notes}">
                    <tr>
                        <td>
                            ${it.id}
                        </td>
                        <td>
                            ${it.dateCreated}
                        </td>
                        <td>
                            ${it.user.toString()}
                        </td>
                        <td>
                            ${it.team.name}
                        </td>
                        <td>
                            ${!it.shared ? 'Shared' : 'Private'}
                        </td>
                    </tr>
                </g:each>
                </tbody>
            </table>
            <center><g:paginate controller="secure" maxsteps="5" action="notes" total="${allNotes}"/></center>
        </g:else>
    </div><!--/span-->
</div><!--/row-->
</body>
</html>