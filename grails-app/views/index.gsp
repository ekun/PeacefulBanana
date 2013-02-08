<html>
<head>
    <meta name="layout" content="main"/>
    <r:require modules="bootstrap"/>
</head>
<body>
	<div class="row-fluid">
	    <div class="span3" style="margin-top: 50px; padding-left: 120px;">
            <g:img file="banana-logo.png" dir="images" />
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