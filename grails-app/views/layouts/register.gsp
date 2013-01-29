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
    <style type="text/css">
    body {
        padding-top: 140px;
        padding-bottom: 40px;
        background-color: #f5f5f5;
    }
    .form-signin {
        max-width: 500px;
        padding: 19px 29px 29px;
        margin: 0 auto 20px;
        background-color: #fff;
        border: 1px solid #e5e5e5;
        -webkit-border-radius: 5px;
        -moz-border-radius: 5px;
        border-radius: 5px;
        -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
        -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
        box-shadow: 0 1px 2px rgba(0,0,0,.05);
    }
    .form-signin h1 {
        margin-bottom: 20px;
    }
    .form-footer {
        margin-top: 25px;
        width: 100%;
    }
    </style>
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