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
    <title>Reflection workshop</title>
    <r:require modules="bootstrap"/>
</head>
<body>
<div class="row-fluid">
    <div class="span3">
        <div class="well sidebar-nav">
            <ul class="nav nav-list">
                <li class="nav-header">Reflection Workshop</li>
                <li class="active"><a href="${createLink(controller: 'workshop')}">Workshops</a></li>
            </ul>
        </div><!--/.well -->
    </div><!--/span-->
    <div class="span9">
        <h1>Create</h1>
        <g:if test="${latestWorkshop}">
            <p>Your latest workshop ended at ${latestWorkshop.dateEnd}.</p>
        </g:if>
        <g:else>
            <p>You have not yet planed any workshops, but your repository was created at ${Repository.findByGithubId(user.selectedRepo).created}, so you might start there?</p>
        </g:else>
        <div id="target"></div>
        <g:form class="form-horizontal">
            <div class="control-group">
                <label class="control-label" for="dateReflectionPeriodeStart">From date</label>
                <div class="controls">
                    <g:datePicker name="dateReflectionPeriodeStart" precision="day" />
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="dateReflectionPeriodeEnd">To date</label>
                <div class="controls">
                    <g:datePicker name="dateReflectionPeriodeEnd" precision="day" />
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