<!--

    This file is part of Peaceful Banana.

    Peaceful Banana is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Peaceful Banana is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Peaceful Banana.  If not, see <http://www.gnu.org/licenses/>.

-->
<%@ page import="org.peaceful.banana.gitdata.Commit" %>
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
            <p>${selectedRepo != null ? selectedRepo?.description : ''}</p>
            <div class="span5">
                <h3>Impact</h3>
                <g:javascript>
                // Load the Visualization API and the piechart package.
                google.load('visualization', '1', {'packages':['corechart']});

                // Set a callback to run when the Google Visualization API is loaded.
                google.setOnLoadCallback(drawChart);

                function drawChart() {
                    var jsonData = $.ajax({
                        url: "${createLink(controller: 'gitData', action: 'impact')}",
                        dataType:"json",
                        async: true,
                        success: function(data) {
                            // Create our data table out of JSON data loaded from server.
                            var data = new google.visualization.DataTable(data);

                            // Instantiate and draw our chart, passing in some options.
                            var chart = new google.visualization.PieChart(document.getElementById('piechart'));
                            chart.draw(data, {width: 300, height: 200});
                        }
                    }).responseText;
                }
                </g:javascript>
                <div id="piechart"></div>
            </div>
            <div class="span5">
                <h3>Configuration</h3>
                Commitcount: ${Commit.countByRepository(selectedRepo)}
            </div>
    	</div><!--/span-->
    </div><!--/row-->
</body>
</html>