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
	                <li class="nav-header">Sidebar</li>
	                <li ${controllerName == null ? 'class="active"' : ''}><a href="${createLinkTo(dir:'')}"><i class="icon-home"></i> Home</a></li>
	                <li ${controllerName.equals('repositories') ? 'class="active"' : ''}><a href="repositories"><i class="icon-folder-open"></i> Repositories</a></li>
	                <li ${controllerName.equals('settings') ? 'class="active"' : ''}><a href="#"><i class="icon-wrench"></i> Settings</a></li>
	            </ul>
	        </div><!--/.well -->
	    </div><!--/span-->
	 	<div class="span9">
  			<h1> Repositories </h1>
    	</div><!--/span-->
    </div><!--/row-->
</body>
</html>