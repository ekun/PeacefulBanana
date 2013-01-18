<%@ page import="org.peaceful.banana.gitdata.Repository" %>

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
        <h1>Teams <a href="${createLink(action: 'create')}" alt="Create team" title="Create team"><i class="mega-icon mega-icon-add"></i></a></h1>
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
                <g:formatTeams teams="${teams}" user="${user}"/>
            </tbody>
        </table>
        <center><g:paginate controller="team" maxsteps="15" action="center" total="${teamsCount}"/></center>
    </div><!--/span-->
</div><!--/row-->
<g:javascript>
    function reloadList() {
        $.ajax({type: "POST",
            url: "${createLink(controller: 'team', action: 'ajaxGetTeamList', params: params)}",
            success: function(msg){
                document.getElementById('target').innerHTML = msg;
            }
        });
    }
</g:javascript>
</body>
</html>