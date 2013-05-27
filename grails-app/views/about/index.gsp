<%@ page import="org.peaceful.banana.User" %>
<!--

    This file is part of Peaceful Banana.

    Peaceful Banana is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Peaceful Banana is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Peaceful Banana.  If not, see <http://www.gnu.org/licenses/>.

-->
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
            <p>
                This program is free software: you can redistribute it and/or modify
                it under the terms of the GNU General Public License as published by
                the Free Software Foundation, either version 3 of the License, or
                (at your option) any later version.

                This program is distributed in the hope that it will be useful,
                but WITHOUT ANY WARRANTY; without even the implied warranty of
                MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
                GNU General Public License for more details.

                You should have received a copy of the GNU General Public License
                along with this program.  If not, see <<a href="http://www.gnu.org/licenses/" target="_blank">http://www.gnu.org/licenses/</a>>.
            </p>
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