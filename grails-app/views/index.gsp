<html>
<head>
    <meta name="layout" content="main"/>
    <r:require modules="bootstrap"/>
</head>
<body>
	<div class="row-fluid">
	    <div class="span3">
            <sec:ifLoggedIn>
	        <div class="well sidebar-nav">
	            <ul class="nav nav-list">
	                <li class="nav-header">Sidebar</li>
	                <li class="active"><a href="${createLinkTo(dir:'')}"><i class="icon-home"></i> Home</a></li>
	                <li><a href="repositories"><i class="icon-folder-open"></i> Repositories</a></li>
                    <li><a href="team"><i class="icon-folder-open"></i> Team</a></li>
	                <li><a href="settings"><i class="icon-wrench"></i> Settings</a></li>
	            </ul>
	        </div><!--/.well -->
            </sec:ifLoggedIn>
	    </div><!--/span-->
	    <div class="span9">
    		<h1> Home </h1>
            <sec:ifLoggedIn>
                <p>Welcome to PeacefulBanana, <b><sec:username/></b>! <br>Happy reflecting!</p>
            </sec:ifLoggedIn>
            <sec:ifNotLoggedIn>
                <p>Welcome to PeacefulBanana!</p>
                <p>In order to take full advantage of the capabilities of PeacefulBanana, you need to login.
                <br>
                If you don't have a user yet, do not worry it is possible to register at any time.</p>
                <p>Here is some of the features you can take advantage of while using PeacefulBanana</p>
                <ul>
                    <li>The full power of Github.com</li>
                    <li>Reflection</li>
                </ul>
            </sec:ifNotLoggedIn>
    	</div><!--/span-->
    </div><!--/row-->
</body>
</html>