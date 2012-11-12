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
  			<h1>Milestones</h1>
            <g:each in="${milestones}">
                <p><b>${it.title}</b></p>
                <p>${it.description}</p>
                <div class="progress">
                    <div class="bar" style="width: ${it.closed.size() > 0 ? ((int)(it.closed.size() / (it.open.size()+it.closed.size()))*100) : 0}%;"></div>
                </div>
                <hr>
            </g:each>
    	</div><!--/span-->
    </div><!--/row-->
</body>
</html>