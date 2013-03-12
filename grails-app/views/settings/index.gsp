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
            <g:if test="${updated}">
                <div class='alert alert-success'>Your password has been updated.</div>
            </g:if>
            <g:form class="form-horizontal" action="index" name='changePasswordForm' method='POST' autocomplete='off'>
                <div class='control-group${error[0]?.error ? ' error': ''}'>
                    <label class="control-label" for="oldPassword">Old Password</label>
                    <div class="controls">
                        <g:field type="password" required="true" name="oldPassword" placeholder="Current password"
                                 value="${error[0]?.value}"/>
                        <span class="help-inline">
                            ${error[0]?.error ? error[0]?.message : ''}
                        </span>
                    </div>
                </div>
                <div class='control-group${error[1]?.error ? ' error': ''}'>
                    <label class="control-label" for="password">New Password</label>
                    <div class="controls">
                        <g:field type="password" required="true" name="password" placeholder="New password"
                                 value="${error[1]?.value}" />
                        <span class="help-inline">
                            ${error[1]?.error ? error[1]?.message : ''}
                        </span>
                    </div>
                </div>
                <div class='control-group${error[2]?.error ? ' error': ''}'>
                    <label class="control-label" for="confirmPassword">Confirm new password</label>
                    <div class="controls">
                        <g:field type="password" required="true" name="confirmPassword" placeholder="Confirm new password"
                                 value="${error[2]?.value}"/>
                        <span class="help-inline">
                            ${error[2]?.error ? error[2]?.message : ''}
                        </span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="submit"></label>
                    <div class="controls">
                        <g:submitButton class="btn btn-danger btn-large" name="submit" value="Change password" />
                    </div>
                </div>
            </g:form>
        </div>
    </div><!--/row-->
</body>
</html>