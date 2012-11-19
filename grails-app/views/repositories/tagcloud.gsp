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
	                <li><a href="${createLink(action: 'milestone')}">Milestones</a></li>
                    <li><a href="${createLink(action: 'issue')}">Issues</a></li>
                    <li class="active"><a href="${createLink(action: 'tagcloud')}">Tagcloud</a></li>
                    <li><a href="${createLink(action: 'statistics')}">Statistics</a></li>
	            </ul>
	        </div><!--/.well -->
	    </div><!--/span-->
	 	<div class="span9">
  			<h1>Tagcloud</h1>
            <div id="tagcloud" class="span9" style="height: 600px;">
                <g:each in="${tags}">
                    <span data-weight="${it.value}">${it.key}</span>
                </g:each>
            </div>
            <g:javascript src="awesomecloud.js" />
            <script>
                var settings = {
                    "size" : {
                        "grid" : 4,
                        "normalize" : true
                    },
                    "options" : {
                        "color" : "random-dark",
                        "printMultiplier" : 3,
                        "sort" : "highest"
                    },
                    "font" : "Futura, Helvetica, sans-serif",
                    "shape" : "square"
                };
                $( "#tagcloud" ).awesomeCloud( settings );
            </script>
    	</div><!--/span-->
    </div><!--/row-->
</body>
</html>