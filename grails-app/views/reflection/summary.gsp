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
                <li class="nav-header">Menu</li>
                <li class="active"><a href="${createLink(action: 'center')}">Summary</a></li>
            </ul>
        </div><!--/.well -->
    </div><!--/span-->
    <div class="span9">
        <div class="row-fluid">
            <div class="pull-left">
                <h1>Impact</h1>
            </div>
            <div class="pull-right">
                <h1 class="pull-right">My Tagcloud</h1>
            </div>
        </div>
        <div class="row-fluid">
            <div id="piechart" class="span6" style="width: 600px;"></div>
            <div id="tagcloud" class="span6 pull-right" style="width: 800px;height: 400px;">
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
        <hr>
        <h3>Something</h3>
        <form class="form-horizontal" action="#">
            <div class="control-group">
                <label class="control-label" for="moodSelector">Mood</label>
                <div class="controls">
                    <select class="span3" id="moodSelector">
                        <option value="1">Very sad</option>
                        <option value="25">Sad</option>
                        <option value="50">Neutral</option>
                        <option value="75">Happy</option>
                        <option value="100">Very happy</option>
                    </select>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="contributions">Top 2 contributions</label>
                <div class="controls">
                    <textarea rows="3" class="span3" id="contributions"></textarea>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="contributions">Top 2 improvements</label>
                <div class="controls">
                    <textarea rows="3" class="span3" id="improvements"></textarea>
                </div>
            </div>
            <div class="control-group">
                <div class="controls">
                    <button type="submit" class="btn">Submit</button>
                </div>
            </div>
        </form>
    </div>
</div><!--/row-->
</body>
</html>