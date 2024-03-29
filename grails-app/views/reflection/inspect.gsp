<!--

    This file is part of Peaceful Banana.

    Peaceful Banana is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Peaceful Banana is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Peaceful Banana.  If not, see <http://www.gnu.org/licenses/>.

-->
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
        <g:if test="${note}">
        <h1 style="margin-bottom: 30px;">${note.user == user ? 'Your note' : note.user.toString() + "'s note"}</h1>
        <p>Here you can see what you wrote for the team '<a href="${createLink(controller: 'team', action: 'inspect', id: note?.team?.id)}">${note?.team?.name}</a>' on ${note.dateCreated}.</p>
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
        </g:if>
    </div><!--/span-->
</div><!--/row-->
</body>
</html>