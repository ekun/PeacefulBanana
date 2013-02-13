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
                    <li class="active"><a href="${createLink(action: 'issue')}">General Issues</a></li>
                    <li><a href="${createLink(action: 'tagcloud')}">Tagcloud</a></li>
	            </ul>
	        </div><!--/.well -->
	    </div><!--/span-->
	 	<div class="span9">
  			<h1>Issues</h1>
            <g:if test="${!issue}">
                <g:each in="${issues}">
                    <g:formatIssues issue="${it}"/>
                </g:each>
            </g:if>
            <g:else>
                <g:formatIssue issue="${issue}"/>
            </g:else>
    	</div><!--/span-->
    </div><!--/row-->
</body>
</html>