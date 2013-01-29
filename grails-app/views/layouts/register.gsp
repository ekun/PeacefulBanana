<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
<html>
<head>
    <title>Peaceful Banana - <g:layoutTitle default="Home"/></title>
    <meta name="viewport" content="initial-scale = 1.0">

    <!-- Le HTML5 shim, for IE6-8 support of HTML elements -->
    <!--[if lt IE 9]>
			<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->

    <!-- Le fav and touch icons -->
    <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
    <link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">

    <link href="${createLinkTo(dir:'css')}/main.css" rel="stylesheet" type="text/css">
    <g:layoutHead/>
    <r:layoutResources />
</head>
<body>
<div class="container-fluid">
    <g:layoutBody/>
</div>
<div id="footer">
    <div class="container">
        <p class="muted credit">© The Plain Penguin Company 2012</p>
    </div>
</div>
<r:layoutResources/>
</body>
</html>