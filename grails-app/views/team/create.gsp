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
        <h3>Create Team</h3>
        <!-- Div for Ajax-response -->
        <div id="response">
            <!-- for ajax swap -->
        </div>
        <!-- Form -->
        <form class="form-horizontal">
            <div class="control-group">
                <label class="control-label" for="inputName">Name</label>
                <div class="controls">
                    <input type="text" id="inputName" name="inputName" placeholder="Name">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="inputRepo">Repository</label>
                <div class="controls">
                    <g:select class="input-xlarge" name="inputRepo"
                              from="${repositories}"
                              optionKey="id" optionValue="${{it.owner.login + '/' + it.name}}"/>
                </div>
            </div>
            <div class="form-actions">
                <g:submitToRemote class="btn btn-primary"  action="ajaxCreateTeam"
                                  update="[success: 'response', failure: 'response']" value="Create" />
                <button type="button" class="btn">Cancel</button>
            </div>
        </form>
        <!-- END Form -->
    </div><!--/span-->
</div><!--/row-->
<g:javascript>
    function
</g:javascript>
</body>
</html>