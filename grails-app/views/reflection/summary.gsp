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
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Daily summary</title>
    <r:require modules="bootstrap"/>
</head>
<body>
<div class="row-fluid">
    <div class="span3">
        <div class="well sidebar-nav">
            <ul class="nav nav-list">
                <li class="nav-header">Reflection</li>
                <li class="active"><a href="${createLink(action: '')}">Notes</a></li>
                <li><a href="${createLink(action: 'preparation')}">Workshop preparation</a></li>
            </ul>
        </div><!--/.well -->
    </div><!--/span-->
    <div class="span9">
        <div class="row-fluid">
            <div class="pull-left">
                <h1><a href="${createLink(action: '')}" alt="Back" title="Back"><i class="mega-icon mega-icon-arr-left"></i></a> Impact</h1>
            </div>
            <div class="pull-right">
                <h1 class="pull-right">My Tagcloud</h1>
            </div>
        </div>
        <div class="row-fluid">
            <div id="piechart" class="span6" style="width: 400px;"></div>
            <div id="tagcloud" class="span6 pull-right" style="width: 500px;height: 250px; z-index: -1;">
            <g:each in="${tagCloud}">
                <span data-weight="${it.value}">${it.key}</span>
            </g:each>
        </div>
    </div>
        <g:javascript src="awesomecloud.js" />
        <g:javascript>
            var settings = {
                "size" : {
                    "grid" : 2,
                    "normalize" : false
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

            // Load the Visualization API and the piechart package.
            google.load('visualization', '1', {'packages':['corechart']});

            // Set a callback to run when the Google Visualization API is loaded.
            google.setOnLoadCallback(drawChart);

            function drawChart() {
                var jsonData = $.ajax({
                    url: "${createLink(controller: 'gitData', action: 'yourimpact')}",
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
        <g:if test="${note && !note.hasErrors()}">
            <div class="alert alert-success">
                <strong>Success!</strong> Your summary has been saved and you can view it <a href="${createLink(action: 'index')}">here</a>.
            </div>
        </g:if>
        <div style="margin-bottom: 50px;">
            <g:summaryForm completed="${submittedForm}"/>
        </div>
    </div>
</div><!--/row-->
</body>
</html>