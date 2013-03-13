<%@ page import="org.peaceful.banana.User" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>About</title>
    <r:require modules="bootstrap"/>
</head>
<body>
	<div class="row-fluid">
	    <div class="span3" style="margin-top: 50px; padding-left: 120px;">
            <g:img file="banana-logo.png" dir="images" />
	    </div><!--/span-->
	    <div class="span9">
    		<h1>About</h1>
            <p>Peacefull Banana is a prototype as a part of a master thesis from NTNU. Written by
            Marius Nedal Glittum & Even Stene</p>
            <p>Current version <g:meta name="app.version"/></p>

            <h3>Technology</h3>
            <p>Built with Grails <g:meta name="app.grails.version"/> and the following plugins for grails
            <ul>
                <li>Twitter Bootstrap v <g:meta name="plugins.twitter-bootstrap" /></li>
                <li>jQuery v. <g:meta name="plugins.jquery-ui"/></li>
                <li>Quartz-schedueler v. <g:meta name="plugins.quartz" /></li>
                <li>Spring Security Core v. <g:meta name="plugins.spring-security-core" /></li>
                <li>Google Visualization v. <g:meta name="plugins.google-visualization" /></li>
                <li>Joda Time v. <g:meta name="plugins.joda-time" /></li>
            </ul>
                
            </p>
    	</div><!--/span-->
    </div><!--/row-->
</body>
</html>