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
                    <g:each var="repo" in="${myRepos}">
	                    <li ${params.getInt('id') == repo.id ? 'class="active"' : ''}><a href="?id=${repo.id}">${repo.isFork() ? '<i class="mini-icon mini-icon-repo-forked"></i>' : '<i class="mini-icon mini-icon-public-repo"></i>'} ${repo.name}</a></li>
                    </g:each>
	            </ul>
	        </div><!--/.well -->
	    </div><!--/span-->
	 	<div class="span9">
  			<h1>${params.get('id') != null ? myRepos.find {it.id == params.getInt("id")}.isFork() ? '<span class="mega-icon mega-icon-repo-forked"></span>' : '<span class="mega-icon mega-icon-public-repo"></span>' : ''} ${params.get('id') != null ? myRepos.find {it.id == params.getInt("id")}.name : 'Repositories'} </h1>
            <p>${params.get('id') != null ? myRepos.find {it.id == params.getInt("id")}.description : ''}</p>
    	</div><!--/span-->
    </div><!--/row-->
</body>
</html>