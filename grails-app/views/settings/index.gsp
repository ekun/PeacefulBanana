<html>
<head>
    <meta name="layout" content="main"/>
    <title>Settings</title>
    <r:require modules="bootstrap"/>
</head>
<body>
	<div class="row-fluid">
	    <div class="span3">
	        <div class="well sidebar-nav">
	            <ul class="nav nav-list">
	                <li class="nav-header">Menu</li>
	                <li class="active"><a href="${createLink(controller: 'settings')}"><i class="mini-icon mini-icon-account-settings"></i> <sec:username/></a></li>
	                <li><a href="${createLink(controller: 'settings', action: 'github')}"><i class="mini-icon mini-icon-blacktocat"></i> Github</a></li>
	            </ul>
	        </div><!--/.well -->
	    </div><!--/span-->
	 	<div class="span9">
  			<h2> Status </h2>
            <p>User data is pulled from <i class="mini-icon mini-icon-blacktocat"></i> Github.com.</p>
            <div class="span2">
                <p>Github integration</p>
                <p>Select a repository</p>
            </div>
            <div class="span6 offset1">
                <p>${gitUser != null ? '<span class="label label-success">Success</span>' : '<span class="label label-important" alt="You have NOT linked your profile with github.">Missing</span>'}</p>
                <p>${user != null && user.selectedRepo != null ? '<span class="label label-success">Success</span>' : '<span class="label label-important" alt="You have NOT selected a repository.">Missing</span>'}</p>
            </div>
    	</div><!--/span-->
        <div class="span9">
            <hr>
            <h2> Change password </h2>
            <div id="target">
                <g:form class="form-horizontal" name='changePasswordForm' method='POST' autocomplete='off'>
                    <p>This will send an email with instructions on how to set a new password.</p>
                    <div class="control-group">
                        <label class="control-label" for="submit"></label>
                        <div class="controls">
                            <g:submitToRemote class="btn btn-danger btn-large" action="ajaxChangePassword" update="target" name="submit" value="Reset password" />
                        </div>
                    </div>
                </g:form>
            </div>
        </div>
    </div><!--/row-->
</body>
</html>