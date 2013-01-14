
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Team Dashboard</title>
    <r:require modules="bootstrap"/>
</head>
<body>
<div class="row-fluid">
    <div class="span3">
        <div class="well sidebar-nav">
            <ul class="nav nav-list">
                <li class="nav-header">Team</li>
                <li><a href="${createLink(action: 'index')}">Dashboard</a></li>
                <li class="active"><a href="${createLink(action: 'my')}">My teams</a></li>
            </ul>
        </div><!--/.well -->
    </div><!--/span-->
    <div class="span9">
        <h3>Dashboard</h3>
        <table class="table table-striped">
            <thead>
            <tr>
                <g:sortableColumn property="name" title="Name" />
                <g:sortableColumn property="repository" title="Repository id" />
                <g:sortableColumn property="members" title="Members" />
                <th></th>
            </tr>
            </thead>
            <tbody id="target">
                <g:each in="${teams}">
                    <tr>
                        <td>
                            ${it.name}
                        </td>
                        <td>
                            ${it.repository}
                        </td>
                        <td>
                            ${it.getMembers().size()}
                        </td>
                        <td>
                            <div class="pull-right">
                                <g:if test="${it == user.activeTeam()}">
                                    Active!
                                </g:if>
                                <g:else>
                                    <g:submitToRemote class="btn btn-danger btn-mini" action="ajaxTrashItem" id="${it?.id}"
                                                      update="feedback"
                                                      value="Archive" onComplete="reloadList();"/>
                                </g:else>
                            </div>
                        </td>
                    </tr>
                </g:each>
            </tbody>
        </table>
        <center><g:paginate controller="notification" maxsteps="15" action="center" total="${teamsCount}"/></center>
    </div><!--/span-->
</div><!--/row-->
</body>
</html>