<%@ page import="org.apache.commons.lang.StringEscapeUtils; org.joda.time.DateTime" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Preparation for reflection workshop</title>
    <r:require modules="bootstrap"/>
</head>
<body>
<div class="row-fluid">
    <div class="span3">
        <div class="well sidebar-nav">
            <ul class="nav nav-list">
                <li class="nav-header">Reflection</li>
                <li class="active"><a href="${createLink(action: 'index')}">Notes</a></li>
                <li><a href="${createLink(action: 'preparation')}">Workshop preparation</a></li>
            </ul>
        </div><!--/.well -->
    </div><!--/span-->
    <div class="span9">
        <h1 style="margin-bottom: 30px;">Your note</h1>
        <p>Here you can see what you wrote for the team '<a href="${createLink(controller: 'team', action: 'inspect', id: note.team.id)}">${note.team.name}</a>' on ${note.dateCreated}.</p>
        <hr>
        <div class="row-fluid">
            <div class="span5">
                <h3>Contributions</h3>
                <p>${note.contributions}</p>
            </div>
            <div class="span5 offset1">
                <h3>Mood</h3>
                <span class="number">${note.mood} / 100</span>
            </div>
        </div>
        <div class="row-fluid">
            <div class="span9">
                <h3>Improvements</h3>
                <p>${note.improvements}</p>
            </div>
        </div>
    </div><!--/span-->
</div><!--/row-->
</body>
</html>