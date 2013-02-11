<html>
<head>
    <meta name="layout" content="main"/>
    <title>Reflection workshop</title>
    <r:require modules="bootstrap"/>
</head>
<body>
<div class="row-fluid">
    <div class="span3">
        <div class="well sidebar-nav">
            <ul class="nav nav-list">
                <li class="nav-header">Reflection Workshop</li>
                <li class="active"><a href="${createLink(action: '')}">Workshops</a></li>
            </ul>
        </div><!--/.well -->
    </div><!--/span-->
    <div class="span9">
        <h1>Workshop</h1>
        <ul style="padding-bottom: 100px;">
            <g:each in="${questions}">
                <li>${it.questionText}</li>
            </g:each>
        </ul>
    </div><!--/span-->
</div><!--/row-->
</body>
</html>