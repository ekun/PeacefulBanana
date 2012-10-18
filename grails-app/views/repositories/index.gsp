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
	                    <li ${params.getInt('id') == repo.ident ? 'class="active"' : ''}><a href="?id=${repo.ident}">${repo.title}</a></li>
                    </g:each>
	            </ul>
	        </div><!--/.well -->
	    </div><!--/span-->
	 	<div class="span9">
  			<h1> ${params.get('id') != null ? myRepos[params.getInt('id')].title : 'Repositories'} </h1>
            <p>${params.get('id') != null ? myRepos[params.getInt('id')].description : ''}</p>
    	</div><!--/span-->
    </div><!--/row-->
</body>
</html>