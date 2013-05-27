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
                <li class="nav-header">Notification Center</li>
                <li><a href="${createLink(action: 'center')}">Inbox</a></li>
                <li class="active"><a href="${createLink(action: 'unread')}">Unread</a></li>
                <li><a href="${createLink(action: 'archive')}">Archived</a></li>
            </ul>
        </div><!--/.well -->
    </div><!--/span-->
    <div class="span9">
        <h3>Unread</h3>
        <table class="table table-striped">
            <thead>
            <tr>
                <g:sortableColumn property="title" title="Subject" />
                <g:sortableColumn property="dateCreated" title="Received" />
                <th></th>
            </tr>
            </thead>
            <tbody>
                <g:formatNotificationList notifications="${unread}"/>
            </tbody>
        </table>
    </div><!--/span-->
</div><!--/row-->
</body>
</html>