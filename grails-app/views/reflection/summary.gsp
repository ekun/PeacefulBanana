<html>
<head>
    <meta name="layout" content="main"/>
    <title>Notification Center</title>
    <r:require modules="bootstrap"/>
</head>
<body>
<div class="row-fluid">
    <div class="span3">
        <div class="well sidebar-nav">
            <ul class="nav nav-list">
                <li class="nav-header">Menu</li>
                <li class="active"><a href="${createLink(action: 'center')}">Notification Center</a></li>
                <li><a href="${createLink(action: 'unread')}">Unread</a></li>
            </ul>
        </div><!--/.well -->
    </div><!--/span-->
    <div>
    <div id="tagcloud" class="span6" style="height: 600px;">
        <g:each in="${tagCloud}">
            <span data-weight="${it.value}">${it.key}</span>
        </g:each>
    </div>
    <g:javascript src="awesomecloud.js" />
        <g:javascript>
            var settings = {
                "size" : {
                    "grid" : 4,
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
        <div id="piechart" class="span6"></div>
    </div>
</div><!--/row-->
</body>
</html>