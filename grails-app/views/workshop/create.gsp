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
        <h1>Create</h1>
        <div id="target"></div>
        <g:form class="form-horizontal">
            <div class="control-group">
                <label class="control-label" for="dateReflectionPeriode">From date</label>
                <div class="controls">
                    <g:datePicker name="dateReflectionPeriode" precision="day" />
                </div>
            </div>
            <div class="control-group">
                <div class="controls">
                    <label class="checkbox">
                    </label>
                    <g:submitToRemote class="btn btn-primary" update="target" value="Create"  action="ajaxCreateWorkshop"/>
                </div>
            </div>
        </g:form>
    </div><!--/span-->
</div><!--/row-->
</body>
</html>