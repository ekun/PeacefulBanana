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
                <li class="active"><a href="${createLink(action: 'index')}">Dashboard</a></li>
            </ul>
        </div><!--/.well -->
    </div><!--/span-->
    <div class="span9">
        <h3>Dashboard</h3>
        <p><a href="${createLink(action: 'inspect')}">${team.name}</a></p>
        <p><a href="${createLink(controller: 'repositories')}">${Repository.findByGithubId(team.repository).name}</a></p>
    </div><!--/span-->
</div><!--/row-->
</body>
</html>