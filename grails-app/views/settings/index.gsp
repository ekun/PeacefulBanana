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
	                <li class="active"><a href="${createLinkTo(dir:'settings')}"><i class="mini-icon mini-icon-account-settings"></i> <sec:username/></a></li>
	                <li><a href="${createLinkTo(dir:'settings')}/github"><i class="mini-icon mini-icon-blacktocat"></i> Github</a></li>
	                <!-- <li ${actionName.equals('settings') ? 'class="active"' : ''}><a href="#"><i class="icon-wrench"></i> Settings</a></li> -->
	            </ul>
	        </div><!--/.well -->
	    </div><!--/span-->
	 	<div class="span9">
  			<h2> Status </h2>
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
            <g:form class="form-horizontal" action='' name='changePasswordForm' method='POST' autocomplete='off'>
                <div class="control-group">
                    <label class="control-label" for="oldPassword">Old Password</label>
                    <div class="controls">
                        <g:field type="password" required="true" name="oldPassword" placeholder="Old password" />
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="oldPassword">Old Password</label>
                    <div class="controls">
                        <g:field type="password" required="true" name="password" placeholder="New password" />
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="oldPassword">Old Password</label>
                    <div class="controls">
                        <g:field type="password" required="true" name="confirmPassword" placeholder="Confirm password" />
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="submit"></label>
                    <div class="controls">
                        <g:submitToRemote class="btn btn-danger btn-large" name="submit" value="Change password" />
                    </div>
                </div>
            </g:form>
        </div>
    </div><!--/row-->
</body>
</html>