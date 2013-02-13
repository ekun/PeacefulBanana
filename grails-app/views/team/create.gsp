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
        <h3>Create Team</h3>
        <div class="alert">Please be aware that this might take a while since we need to sync with github inorder to create a team</div>
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
                <g:submitToRemote class="btn btn-primary"  action="ajaxCreateTeam" id="createTeam"
                                  update="[success: 'response', failure: 'response']" value="Create" onSuccess="success()"
                                  onFailure="failure()" onclick="loading()"/>
            </div>
        </form>
        <!-- END Form -->
    </div><!--/span-->
</div><!--/row-->
<g:javascript>
    function success() {
        $("#createTeam").removeClass("btn-primary");
        $("#createTeam").addClass("btn-success");
        $("#createTeam").attr("value", "Created");
    }

    function failure() {
        $("#createTeam").removeClass("btn-primary");
        $("#createTeam").addClass("btn-danger");
        $("#createTeam").attr("value", "Error..");
    }

    function loading() {
        $("#createTeam").attr("disabled", "disabled");
        $("#createTeam").addClass("disabled");
        $("#createTeam").attr("value", "Creating..");
    }
</g:javascript>
</body>
</html>