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
                    <li class="divider"></li>
                    <li class="nav-header">Milestones</li>
                    <g:each in="${selectedRepo.milestones}">
                        <li><a href="${createLink(id: it.id)}">${it.title}<span class="label pull-right${it?.state == "closed" ? ' label-important">Closed' : it.dueOn?.before(new Date(System.currentTimeMillis())) ? ' label-warning">Overdue' : ' label-success">Open'}</span></a></li>
                    </g:each>
	            </ul>
	        </div><!--/.well -->
	    </div><!--/span-->
	 	<div class="span9">
            <ul class="nav nav-tabs">
                <li ${!params.get("id") ? 'class="active"' : ''}>
                    <a href="${createLink(action: 'milestone')}">Open</a>
                </li>
                <li ${params.get("id")=="overdue" ? 'class="active"' : ''}>
                    <a href="${createLink(action: 'milestone', id: 'overdue')}">Overdue
                        ${selectedRepo.getMilestones().count {it.dueOn?.before(new Date(System.currentTimeMillis())) && it.state == "open"}.intValue() > 0 ? "<span class='badge badge-important'>"+selectedRepo.getMilestones().count {it.dueOn!= null && it.dueOn.before(new Date(System.currentTimeMillis())) && it.state == "open"}.intValue() + "</span>" : ""}</a>
                </li>
                <li ${params.get("id")=="closed" ? 'class="active"' : ''}>
                    <a href="${createLink(action: 'milestone', id: 'closed')}">Closed</a>
                </li>
                <li ${params.get("id")=="all" ? 'class="active"' : ''}>
                    <a href="${createLink(action: 'milestone', id: 'all')}">All</a>
                </li>
            </ul>
            <g:each in="${milestones}">
                <g:formatMilestone milestone="${it}"/>
            </g:each>
    	</div><!--/span-->
    </div><!--/row-->
</body>
</html>