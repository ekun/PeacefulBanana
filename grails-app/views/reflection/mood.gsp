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
    </div><!--/span-->
</div><!--/row-->
</body>
</html>