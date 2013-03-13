<%@ page import="org.joda.time.DateTime" %>
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
                <li class="active"><a href="${createLink(controller: 'workshop', action: 'index')}">Workshops</a></li>
            </ul>
        </div><!--/.well -->
    </div><!--/span-->
    <div class="span9">
        <h1>Workshop <a href="${createLink(action: 'create')}" alt="Create workshop" title="Create workshop"><i class="mega-icon mega-icon-add"></i></a></h1>
        <g:each in="${workshops}">
            <a href="${createLink(controller: 'workshop', action: 'inspect', id: it.id)}">${it.getDuration()} from
                <joda:time value="${new DateTime(it.dateStart)}">
                    <joda:format value="${it}" pattern="EEE, d MMM yyyy"/>
                </joda:time>
                to
                <joda:time value="${new DateTime(it.dateEnd)}">
                    <joda:format value="${it}" pattern="EEE, d MMM yyyy"/>
                </joda:time>
            </a>
            <br>
        </g:each>
    </div><!--/span-->
</div><!--/row-->
</body>
</html>