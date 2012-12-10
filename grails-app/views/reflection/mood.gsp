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
        var data = new google.visualization.DataTable();
        data.addColumn('date', 'Date');
        data.addColumn('number', 'Sold Pencils');
        data.addColumn('string', 'title1');
        data.addColumn('string', 'text1');
        data.addColumn('number', 'Sold Pens');
        data.addColumn('string', 'title2');
        data.addColumn('string', 'text2');
        data.addRows([
            [new Date(2008, 1 ,1), 30000, undefined, undefined, 40645, undefined, undefined],
            [new Date(2008, 1 ,2), 14045, undefined, undefined, 20374, undefined, undefined],
            [new Date(2008, 1 ,3), 55022, undefined, undefined, 50766, undefined, undefined],
            [new Date(2008, 1 ,4), 75284, undefined, undefined, 14334, 'Out of Stock','Ran out of stock on pens at 4pm'],
            [new Date(2008, 1 ,5), 41476, 'Bought Pens','Bought 200k pens', 66467, undefined, undefined],
            [new Date(2008, 1 ,6), 33322, undefined, undefined, 39463, undefined, undefined]
        ]);

        var chart = new google.visualization.AnnotatedTimeLine(document.getElementById('chart_div'));
        chart.draw(data, {displayAnnotations: true});
    }
</g:javascript>
</body>
</html>