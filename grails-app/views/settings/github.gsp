<html>
<head>
    <meta name="layout" content="main"/>
    <r:require modules="bootstrap"/>
</head>
<body>
	<div class="row-fluid">
	    <div class="span3">
	        <div class="well sidebar-nav">
	            <ul class="nav nav-list">
	                <li class="nav-header">Menu</li>
	                <li ${actionName.equals('index') ? 'class="active"' : ''}>
                        <a href="${createLinkTo(dir:'settings')}"><i class="mini-icon mini-icon-account-settings"></i> <sec:username/></a>
                    </li>
	                <li ${actionName.equals('github') ? 'class="active"' : ''}>
                        <a href="${createLinkTo(dir:'settings')}/github"><i class="mini-icon mini-icon-blacktocat"></i> GitHub</a>
                    </li>
	                <!-- <li ${actionName.equals('settings') ? 'class="active"' : ''}><a href="#"><i class="icon-wrench"></i> Settings</a></li> -->
	            </ul>
	        </div><!--/.well -->
	    </div><!--/span-->
	 	<div class="span9">
  			<h1> github </h1>
            <p style="${gitUser != null ? 'display: none;' : ''}"><oauth:connect provider="github">Link your account with GitHub</oauth:connect></p>
            <p style="${gitUser == null ? 'display: none;' : ''}">
                <g:form name="changeRepo" action="changeSelectedRepo" class="form-inline">
                    <g:select class="input-xlarge" name="repoSelection"
                          from="${repositories}"
                          value="${user?.selectedRepo}"
                          optionKey="id" optionValue="${{it.owner.login + '/' + it.name}}" onchange="resetButton()"/>
                    <g:submitToRemote class="btn btn-primary" action="changeSelectedRepo" id="changeRepoButton"
                                      update="[success: 'message', failure: 'error']" onLoading="loading()" onSuccess="success()" onFailure="failure()" value="Save" />
                </g:form>
                <g:javascript>
                    function success() {
                        $("#changeRepoButton").removeClass("btn-primary");
                        $("#changeRepoButton").addClass("btn-success");
                        $("#changeRepoButton").attr("value", "Saved..");
                    }

                    function failure() {
                        $("#changeRepoButton").removeClass("btn-primary");
                        $("#changeRepoButton").addClass("btn-danger");
                        $("#changeRepoButton").attr("value", "Error..");
                    }

                    function loading() {
                        $("#changeRepoButton").attr("disabled", "disabled");
                        $("#changeRepoButton").addClass("disabled");
                        $("#changeRepoButton").attr("value", "Saving..");
                    }

                    function resetButton() {
                        $("#changeRepoButton").removeClass("btn-success");
                        $("#changeRepoButton").addClass("btn-primary");
                        $("#changeRepoButton").attr("value", "Save");
                    }
                </g:javascript>
            </p>
        <div id="message"></div>
        <div id="error"></div>
    </div><!--/span-->
    </div><!--/row-->
</body>
</html>