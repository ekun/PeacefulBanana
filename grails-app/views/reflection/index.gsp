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
    <title>Reflection Center</title>
    <r:require modules="bootstrap"/>
</head>
<body>
<div class="row-fluid">
    <div class="span3">
        <div class="well sidebar-nav">
            <ul class="nav nav-list">
                <li class="nav-header">Reflection</li>
                <li class="active"><a href="${createLink(action: '')}">Notes</a></li>
                <li><a href="${createLink(action: 'preparation')}">Workshop preparation</a></li>
            </ul>
        </div><!--/.well -->
    </div><!--/span-->
    <div class="span9">
        <h1>Notes <a href="${createLink(action: 'summary')}" alt="Create note" title="Create note"><i class="mega-icon mega-icon-add"></i></a></h1>
        <ul class="nav nav-tabs">
            <li ${!params.get("id") ? 'class="active"' : ''}>
                <a href="${createLink(action: '')}">My notes</a>
            </li>
            <li ${params.get("id")=="shared" ? 'class="active"' : ''}>
                <a href="${createLink(action: '', id: 'shared')}">Shared notes</a>
            </li>
        </ul>
        <div id="feedback"></div>
        <table class="table table-striped">
            <thead>
            <tr>
                <g:sortableColumn property="id" title="#" />
                <g:sortableColumn property="dateCreated" title="Created" />
                <g:sortableColumn property="user" title="User" />
                <g:sortableColumn property="team" title="Team" />
                <th></th>
            </tr>
            </thead>
            <tbody id="target">
                <g:formatNotesList notes="${notes}"/>
            </tbody>
        </table>
        <center><g:paginate controller="reflection" maxsteps="5" action="index" total="${notesCount}"/></center>
    </div><!--/span-->
</div><!--/row-->
<g:javascript>
    function reloadList() {
        $.ajax({type: "POST",
            url: "${createLink(controller: 'reflection', action: 'ajaxGetNoteList', params: params)}",
            success: function(msg){
                document.getElementById('target').innerHTML = msg;
            }
        });
    }
</g:javascript>
</body>
</html>