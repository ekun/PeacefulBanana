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
<%@ page import="org.peaceful.banana.gitdata.Repository" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Team Dashboard</title>
    <r:require modules="bootstrap"/>
</head>
<body>
<div class="row-fluid">
    <div class="span3">
        <div class="well sidebar-nav">
            <ul class="nav nav-list">
                <li class="nav-header">Team</li>
                <li class="active"><a href="${createLink(action: 'index')}">Dashboard</a></li>
            </ul>
        </div><!--/.well -->
    </div><!--/span-->
    <div class="span9">
        <h1>Teams <a href="${createLink(action: 'create')}" alt="Create team" title="Create team"><i class="mega-icon mega-icon-add"></i></a></h1>
        <div id="feedback"></div>
        <table class="table table-striped">
            <thead>
            <tr>
                <g:sortableColumn property="name" title="Name" />
                <g:sortableColumn property="repository" title="Repository" />
                <g:sortableColumn property="members" title="Members" />
                <th></th>
            </tr>
            </thead>
            <tbody id="target">
                <g:formatTeams teams="${teams}" user="${user}"/>
            </tbody>
        </table>
        <center><g:paginate controller="team" maxsteps="15" action="center" total="${teamsCount}"/></center>
        <hr>
        <g:if test="${availibleTeamsBasedOnRepos != null}"></g:if>
        <h1>Available teams</h1>
        <p>Based on your github repositories</p>
        <table class="table table-striped">
            <thead>
            <tr>
                <g:sortableColumn property="name" title="Name" />
                <g:sortableColumn property="repository" title="Repository" />
                <g:sortableColumn property="members" title="Members" />
                <th></th>
            </tr>
            </thead>
            <tbody id="target2">
            </tbody>
        </table>
        <center><g:paginate controller="team" maxsteps="15" action="center" total="${availTeamCount - teamsCount?.size()}"/></center>

    </div><!--/span-->
    </div><!--/row-->
    <g:javascript>

        $(document).ready(function() {
            reloadAvailList();
        });

        function reloadList() {
            $.ajax({type: "POST",
                url: "${createLink(controller: 'team', action: 'ajaxGetTeamList', params: params)}",
                success: function(msg){
                    document.getElementById('target').innerHTML = msg;
                }
            });
        }

        function reloadAvailList() {
            $.ajax({type: "POST",
                url: "${createLink(controller: 'team', action: 'ajaxGetAvailTeamList', params: params)}",
                success: function(msg){
                    document.getElementById('target2').innerHTML = msg;
                }
            });
            reloadList();
        }
    </g:javascript>
    </div><!--/row-->
</body>
</html>