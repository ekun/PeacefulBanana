<html>
<head>
    <meta name="layout" content="main"/>
    <title>Tagcloud</title>
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
            <div class="row-fluid">
                <div class="pull-left">
                      <h1>Team tagcloud</h1>
                </div>
                <div class="pull-right">
                    <h1 class="pull-right">My tagcloud</h1>
                </div>
            </div>
            <div class="row-fluid">
                <div id="tagcloud" class="span6" style="height: 800px;">
                <g:each in="${tags}">
                    <span data-weight="${it.value}">${it.key}</span>
                </g:each>
                </div>
                <div id="mytagcloud" class="span6" style="height: 800px;">
                    <g:each in="${mytags}">
                        <span data-weight="${it.value}">${it.key}</span>
                    </g:each>
                </div>
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
                var mySettings = {
                    "size" : {
                        "grid" : 4,
                        "normalize" : true
                    },
                    "options" : {
                        "color" : "random-light",
                        "printMultiplier" : 3,
                        "sort" : "highest"
                    },
                    "font" : "Futura, Helvetica, sans-serif",
                    "shape" : "square"
                };
                $( "#tagcloud" ).awesomeCloud( settings );
                $( "#mytagcloud" ).awesomeCloud( mySettings );
            </script>
    	</div><!--/span-->
    </div><!--/row-->
</body>
</html>