<html>
<head>
    <meta name="layout" content="main"/>
    <title>Issues</title>
    <r:require modules="bootstrap"/>
</head>
<body>
	<div class="row-fluid">
	    <div class="span3">
	        <div class="well sidebar-nav">
	            <ul class="nav nav-list">
	                <li class="nav-header">Menu</li>
                    <li><a href="${createLink(action: '')}">${selectedRepo.name}</a></li>
	                <li><a href="${createLink(action: 'milestone')}">Milestones</a></li>
                    <li class="active"><a href="${createLink(action: 'issue')}">Issues</a></li>
                    <li><a href="${createLink(action: 'tagcloud')}">Tagcloud</a></li>
                    <li><a href="${createLink(action: 'statistics')}">Statistics</a></li>
                    <li class="nav-header">Team</li>
                    <li><a href="${createLink(action: '')}">Members</a></li>
                    <li><a href="${createLink(action: 'mood')}">Mood</a></li>
	            </ul>
	        </div><!--/.well -->
	    </div><!--/span-->
	 	<div class="span9">
  			<h1>Issues</h1>
            <g:each in="${issues}">
                <g:formatIssues issue="${it}"/>
            </g:each>
            <g:formatIssue issue="${issue}"/>
    	</div><!--/span-->
    </div><!--/row-->
</body>
</html>