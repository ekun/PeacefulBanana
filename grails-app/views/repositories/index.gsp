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
                    <li><a href="${createLink(action: '')}">${selectedRepo.owner.login + "/" + selectedRepo.name}</a></li>
	                <li><a href="${createLink(action: 'milestone')}">Milestones</a></li>
                    <li><a href="">Issues</a></li>
                    <li><a href="">Tagcloud</a></li>
                    <li><a href="${createLink(action: 'statistics')}">Statistics</a></li>
	            </ul>
	        </div><!--/.well -->
	    </div><!--/span-->
	 	<div class="span9">
  			<h1>
                  ${selectedRepo != null ? (selectedRepo.isPrivate() ? '<span class="mega-icon mega-icon-private-repo"></span>' : (selectedRepo.isFork() ? '<span class="mega-icon mega-icon-repo-forked"></span>' : '<span class="mega-icon mega-icon-public-repo"></span>')) : ''} ${selectedRepo != null ? (selectedRepo.owner.login + "/" + selectedRepo.name) : 'Repositories'} </h1>
            <p>${selectedRepo != null ? selectedRepo.description : ''}</p>
            ${selectedRepo != null ? '<h2> Commits </h2>' : ''}
    	</div><!--/span-->
    </div><!--/row-->
</body>
</html>