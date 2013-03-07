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
                <li class="active"><a href="${createLink(action: 'index')}">Dashboard</a></li>
            </ul>
        </div><!--/.well -->
    </div><!--/span-->
    <div class="span9">
        <h3>${team.name}</h3>
        <div id="feedback"></div>
        <table class="table table-striped">
            <thead>
            <tr>
                <g:sortableColumn property="lastName" title="Name" />
                <th><div class="pull-right">Role</div></th>
            </tr>
            </thead>
            <tbody id="target">
                <g:formatTeamMembers users="${teamMembers}" user="${user}"/>
            </tbody>
        </table>
        <center><g:paginate controller="team" maxsteps="5" action="inspect" total="${team.getMembers().size()}"/></center>
    </div><!--/span-->
</div><!--/row-->
<g:javascript>
    function reloadList() {
            $.ajax({type: "POST",
                url: "${createLink(controller: 'team', action: 'ajaxGetTeamMemberList', params: params)}",
                success: function(msg){
                    document.getElementById('target').innerHTML = msg;
                }
            });
        }
</g:javascript>
</body>
</html>