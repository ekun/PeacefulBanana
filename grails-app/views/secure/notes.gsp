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
<%@ page import="org.joda.time.DateTime; org.peaceful.banana.User; org.peaceful.banana.Team; org.peaceful.banana.reflection.Note" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Admin - Notes</title>
    <r:require modules="bootstrap"/>
</head>
<body>
<div class="row-fluid">
    <div class="span3">
        <div class="well sidebar-nav">
            <ul class="nav nav-list">
                <li class="nav-header">Administration</li>
                <li><a href="${createLink(action: 'index')}">Dashboard</a></li>
                <li><a href="${createLink(action: 'users')}">Users</a></li>
                <li><a href="${createLink(action: 'notes')}">Reflection</a></li>
                <ul class="nav nav-list">
                    <li ${!params.get("id") ? 'class="active"' : ''}><a href="${createLink(action: 'notes')}">Notes</a></li>
                    <li ${params.get("id") == "stats" ? 'class="active"' : ''}><a href="${createLink(action: 'notes', id: 'stats')}">Statistics</a></li>
                    <li ${params.get("id") == "mood" ? 'class="active"' : ''}><a href="${createLink(action: 'notes', id: 'mood')}">Mood</a></li>
                </ul>
                <li><a href="${createLink(action: 'github')}">Github</a></li>
            </ul>
        </div><!--/.well -->
    </div><!--/span-->
    <div class="span9">
        <g:if test="${params.get("id") == "stats"}">
            <h3>Statistics</h3>
            <p>Some statistics about the data in every note created</p>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>Total</th>
                    <th>per team</th>
                    <th>per user</th>
                    <th>average mood</th>
                    <th>shared ratio</th>
                </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>
                            ${Note.count}
                        </td>
                        <td>
                            <g:formatNumber number="${(Note.count / Team.count)}" type="number"
                                            maxFractionDigits="2" roundingMode="HALF_DOWN" />
                        </td>
                        <td>
                            <g:formatNumber number="${(Note.count / User.count)}" type="number"
                                            maxFractionDigits="2" roundingMode="HALF_DOWN" />
                        </td>
                        <td>
                            <g:formatNumber number="${(Note.findAll().mood.sum() / Note.count)}" type="number"
                                            maxFractionDigits="2" roundingMode="HALF_DOWN" />
                        </td>
                        <td>
                            <g:formatNumber number="${(Note.countByShared(true) / Note.count)}" type="percent"
                                            maxFractionDigits="2" roundingMode="HALF_DOWN" />
                        </td>
                    </tr>
                </tbody>
            </table>
        </g:if>
        <g:elseif test="${params.get("id") == "mood"}">
            <h3>Mood</h3>
            <p>Here you can see the mood of the different teams.</p>
        </g:elseif>
        <g:else>
            <h3>Notes</h3>
            <table class="table table-striped">
                <thead>
                <tr>
                    <g:sortableColumn property="id" title="#" />
                    <g:sortableColumn property="dateCreated" title="Created" />
                    <g:sortableColumn property="user" title="User" />
                    <g:sortableColumn property="team" title="Team" />
                    <g:sortableColumn property="shared" title="Team" />
                </tr>
                </thead>
                <tbody id="target">
                <g:each in="${notes}">
                    <tr>
                        <td>
                            ${it.id}
                        </td>
                        <td>
                            <joda:time value="${new DateTime(it.dateCreated)}">
                                <joda:format value="${it}" pattern="yyyy-MM-dd HH:mm:ss" />
                            </joda:time>
                        </td>
                        <td>
                            ${it.user.toString()}
                        </td>
                        <td>
                            ${it.team.name}
                        </td>
                        <td>
                            ${!it.shared ? 'Shared' : 'Private'}
                        </td>
                    </tr>
                </g:each>
                </tbody>
            </table>
            <center><g:paginate controller="secure" maxsteps="5" action="notes" total="${allNotes}"/></center>
        </g:else>
    </div><!--/span-->
</div><!--/row-->
</body>
</html>