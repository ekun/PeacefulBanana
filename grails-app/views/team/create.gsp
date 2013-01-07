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
            </ul>
        </div><!--/.well -->
    </div><!--/span-->
    <div class="span9">
        <h3>Create Team</h3>
        <!-- Form -->
        <div id="response"> <!-- for ajax swap -->
            <div class="form-actions">
                <button type="submit" class="btn btn-primary">Save changes</button>
                <button type="button" class="btn">Cancel</button>
            </div>
        </div>
        <!-- END Form -->
    </div><!--/span-->
</div><!--/row-->
</body>
</html>