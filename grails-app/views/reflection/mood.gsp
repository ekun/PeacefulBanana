<html>
<head>
    <meta name="layout" content="main"/>
    <title>Reflection Center</title>
    <r:require modules="bootstrap"/>
</head>
<body>
<div class="row-fluid">
    <div class="span3">
        <div class="well sidebar-nav">
            <ul class="nav nav-list">
                <li class="nav-header">Reflection</li>
                <li><a href="${createLink(action: 'index')}">Notes</a></li>
                <li><a href="${createLink(action: 'summary')}">Summary</a></li>
                <li class="active"><a href="${createLink(action: 'mood')}">Mood</a></li>
            </ul>
        </div><!--/.well -->
    </div><!--/span-->
    <div class="span9">
        <h3>Mood</h3>
        <p>Here you will be presented with a mood-graph.</p>
        <div id='chart_div' style='width: 700px; height: 480px;'></div>
    </div><!--/span-->
</div><!--/row-->
<g:javascript>
    google.load('visualization', '1', {'packages':['annotatedtimeline']});
    google.setOnLoadCallback(drawChart);
    function drawChart() {
        var jsonData = $.ajax({
            url: "${createLink(controller: 'reflectionData', action: 'mood')}",
            dataType:"json",
            async: true,
            success: function(jsdata) {
                // Create our data table out of JSON data loaded from server.
                var data = new google.visualization.DataTable(jsdata);

                // Instantiate and draw our chart, passing in some options.
                var chart = new google.visualization.AnnotatedTimeLine(document.getElementById('chart_div'));
                chart.draw(data, {width: 700, height: 480, displayAnnotations: false});
            }
        }).responseText;
    }
</g:javascript>
</body>
</html>