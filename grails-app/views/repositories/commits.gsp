<%@ page import="org.peaceful.banana.User; org.peaceful.banana.gitdata.Commit" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>${selectedRepo != null ? (selectedRepo.name) : 'Repository'}</title>
    <r:require modules="bootstrap"/>
</head>
<body>
	<div class="row-fluid">
	    <div class="span3">
	        <div class="well sidebar-nav">
	            <ul class="nav nav-list">
	                <li class="nav-header">Menu</li>
                    <li class="active"><a href="${createLink(action: '')}">${selectedRepo?.name}</a></li>
	                <li><a href="${createLink(action: 'milestone')}">Milestones</a></li>
                    <li><a href="${createLink(action: 'issue')}">General Issues</a></li>
                    <li><a href="${createLink(action: 'tagcloud')}">Tagcloud</a></li>
	            </ul>
	        </div><!--/.well -->
	    </div><!--/span-->
	 	<div class="span9">
  			<h1>${selectedRepo != null ? (selectedRepo?.name) : ''} </h1>
            Commit count: ${commits.size()}
            <table class="table table-hover">
                <thead>
                    <tr>
                        <g:sortableColumn property="login" title="User" style="width: 150px;" />
                        <g:sortableColumn property="createdAt" title="Timestamp" style="width: 170px;" />
                        <g:sortableColumn property="message" title="Message" />
                    </tr>
                </thead>
                <tbody>
                    <g:each in="${commits}">
                        <tr>
                            <td>${User.findByGitLogin(it.login) ? User.findByGitLogin(it.login).toString() : it.login}</td>
                            <td>${it.createdAt}</td>
                            <td>${it.message}</td>
                        </tr>
                    </g:each>
                </tbody>


            </table>
    	</div><!--/span-->
    </div><!--/row-->
</body>
</html>