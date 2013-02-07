<html>
<head>
    <meta name="layout" content="main"/>
    <title>Preperation for reflection workshop</title>
    <r:require modules="bootstrap"/>
</head>
<body>
<div class="row-fluid">
    <div class="span3">
        <div class="well sidebar-nav">
            <ul class="nav nav-list">
                <li class="nav-header">Reflection Workshop</li>
                <li><a href="${createLink(action: '')}">Index</a></li>
                <li class="active"><a href="${createLink(action: 'preperation')}">Preperation</a></li>
            </ul>
        </div><!--/.well -->
    </div><!--/span-->
    <div class="span9">
        <h1 style="margin-bottom: 30px;">Preperation</h1>
        <hr>
        <div class="row">
            <div class="span5" style="margin-left: 30px;">
                <h3>Contributions</h3>
                <ul>
                    <g:each in="${notes}">
                        <li>${it.contributions}</li>
                    </g:each>
                </ul>
            </div>
            <div class="span5 offset1">
                <h3>Improvements</h3>
                <ul>
                    <g:each in="${notes}">
                        <li>${it.improvements}</li>
                    </g:each>
                </ul>
            </div>
        </div>
    </div><!--/span-->
</div><!--/row-->
</body>
</html>