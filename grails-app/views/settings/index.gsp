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
	                <li ${actionName.equals('index') ? 'class="active"' : ''}><a href="${createLinkTo(dir:'settings')}"><i class="mini-icon mini-icon-account-settings"></i> <sec:username/></a></li>
	                <li ${actionName.equals('github') ? 'class="active"' : ''}><a href="${createLinkTo(dir:'settings')}/github"><i class="mini-icon mini-icon-blacktocat"></i> Github</a></li>
	                <!-- <li ${actionName.equals('settings') ? 'class="active"' : ''}><a href="#"><i class="icon-wrench"></i> Settings</a></li> -->
	            </ul>
	        </div><!--/.well -->
	    </div><!--/span-->
	 	<div class="span9">
  			<h1> Settings </h1>
            <div class="span2">
                <p>Github integration</p>
                <p>Select a repository</p>
            </div>
            <div class="span6 offset1">
                <p>${gitUser != null ? '<span class="label label-success">Success</span>' : '<span class="label label-important" alt="You have NOT linked your profile with github.">Important</span>'}</p>
                <p>${repository != null ? '<span class="label label-success">Success</span>' : '<span class="label label-important" alt="You have NOT selected a repository.">Important</span>'}</p>
            </div>
    	</div><!--/span-->
    </div><!--/row-->
</body>
</html>