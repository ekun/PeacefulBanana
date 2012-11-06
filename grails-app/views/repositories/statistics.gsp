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
	                <li><a href="${createLink(action: 'milestone')}">Milestones</a></li>
                    <li><a href="${createLink(action: 'issue')}">Issues</a></li>
                    <li><a href="${createLink(action: 'tagcloud')}">Tagcloud</a></li>
                    <li class="active"><a href="${createLink(action: 'statistics')}">Statistics</a></li>
	            </ul>
	        </div><!--/.well -->
	    </div><!--/span-->
	 	<div class="span9">
  			<h1>Impact</h1>
            <div id="spinner" class="spinner">
                <img src="${resource(dir: 'images', file: 'spinner.gif')}" alt="Spinner"/>
            </div>
            <g:javascript>
                // Load the Visualization API and the piechart package.
                google.load('visualization', '1', {'packages':['corechart']});

                // Set a callback to run when the Google Visualization API is loaded.
                google.setOnLoadCallback(drawChart);

                function drawChart() {
                    var jsonData = $.ajax({
                        url: "${createLink(controller: 'gitData', action: 'impact', id: user.selectedRepo)}",
                        dataType:"json",
                        async: true,
                        success: function(data) {
                            // Create our data table out of JSON data loaded from server.
                            var data = new google.visualization.DataTable(data);

                            // Instantiate and draw our chart, passing in some options.
                            var chart = new google.visualization.PieChart(document.getElementById('piechart'));
                            chart.draw(data, {width: 600, height: 400});
                        }
                    }).responseText;
                }
            </g:javascript>
            <div id="piechart"></div>
    	</div><!--/span-->
    </div><!--/row-->
</body>
</html>