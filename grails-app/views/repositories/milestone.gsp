<%@ page import="org.peaceful.banana.gitdata.Issue" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Milestones</title>
    <r:require modules="bootstrap"/>
</head>
<body>
	<div class="row-fluid">
	    <div class="span3">
	        <div class="well sidebar-nav">
	            <ul class="nav nav-list">
	                <li class="nav-header">Menu</li>
                    <li><a href="${createLink(action: '')}">${selectedRepo.name}</a></li>
	                <li class="active"><a href="${createLink(action: 'milestone')}">Milestones</a></li>
                    <li><a href="${createLink(action: 'issue')}">Issues</a></li>
                    <li><a href="${createLink(action: 'tagcloud')}">Tagcloud</a></li>
                    <li><a href="${createLink(action: 'statistics')}">Statistics</a></li>
	            </ul>
	        </div><!--/.well -->
	    </div><!--/span-->
	 	<div class="span9">
            <ul class="nav nav-tabs">
                <li ${!params.get("id") ? 'class="active"' : ''}>
                    <a href="${createLink(action: 'milestone')}">All</a>
                </li>
                <li ${params.get("id")=="overdue" ? 'class="active"' : ''}>
                    <a href="${createLink(action: 'milestone', id: 'overdue')}">Overdue</a>
                </li>
                <li ${params.get("id")=="open" ? 'class="active"' : ''}>
                    <a href="${createLink(action: 'milestone', id: 'open')}">Open</a>
                </li>
                <li ${params.get("id")=="closed" ? 'class="active"' : ''}>
                    <a href="${createLink(action: 'milestone', id: 'closed')}">Closed</a>
                </li>
            </ul>
            <g:each in="${milestones}">
                <g:formatMilestone milestone="${it}"/>
            </g:each>
    	</div><!--/span-->
    </div><!--/row-->
</body>
</html>