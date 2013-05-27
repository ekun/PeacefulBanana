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
    <title>Reflection workshop</title>
    <r:require modules="bootstrap"/>
    <r:require modules="bootstrap-transition"/>
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
        <h1>Workshop <a href="${createLink(controller: 'workshop', action: 'export', id: params.id)}" download class="pull-right">
            <img src="${resource(dir: 'images', file: 'pdf.gif')}" width="30px" alt="Download pdf">
        </a></h1>
        <div id="target">
            <g:formatWorkshopQuestions questions="${questions}" tags="${tags}" />
        </div>
    </div><!--/span-->
</div><!--/row-->
<g:javascript>
    function reloadList() {
            $.ajax({type: "POST",
                url: "${createLink(controller: 'workshop', action: 'ajaxGetQuestions', params: params)}",
                success: function(msg){
                    document.getElementById('target').innerHTML = msg;
                    $("#collapseOne").collapse('hide');
                    $("#collapseTwo").collapse('show');
                }
            });

        }

    function reloadList2() {
            $.ajax({type: "POST",
                url: "${createLink(controller: 'workshop', action: 'ajaxGetQuestions', params: params)}",
                success: function(msg){
                    document.getElementById('target').innerHTML = msg;
                    $("#collapseOne").collapse('hide');
                    $("#collapseThree").collapse('show');
                }
            });

        }
</g:javascript>
</body>
</html>