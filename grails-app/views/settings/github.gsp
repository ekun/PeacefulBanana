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
	                <li ${actionName.equals('index') ? 'class="active"' : ''}><a href="${createLinkTo(dir:'settings')}"><i class="mini-icon mini-icon-account-settings"></i> <sec:username/></a></li>
	                <li ${actionName.equals('github') ? 'class="active"' : ''}><a href="${createLinkTo(dir:'settings')}/github"><i class="mini-icon mini-icon-blacktocat"></i> GitHub</a></li>
	                <!-- <li ${actionName.equals('settings') ? 'class="active"' : ''}><a href="#"><i class="icon-wrench"></i> Settings</a></li> -->
	            </ul>
	        </div><!--/.well -->
	    </div><!--/span-->
	 	<div class="span9">
  			<h1> github </h1>
            <oauth:connect provider="github">Link your account with GitHub</oauth:connect>
    	</div><!--/span-->
    </div><!--/row-->
</body>
</html>