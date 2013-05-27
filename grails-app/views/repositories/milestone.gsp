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
<%@ page import="org.peaceful.banana.gitdata.Issue" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Milestones</title>
    <r:require modules="bootstrap"/>
</head>
<body>
	<div class="row-fluid">
	    <div class="span3">
	        <div class="well sidebar-nav">
	            <ul class="nav nav-list">
	                <li class="nav-header">Menu</li>
                    <li><a href="${createLink(action: '')}">${selectedRepo.name}</a></li>
	                <li ${!params.getLong("id") ? 'class="active"' : ''}><a href="${createLink(action: 'milestone')}">Milestones</a></li>
                    <ul class="nav nav-list">
                        <g:each in="${selectedRepo.milestones.sort {it.state}.reverse()}">
                            <li ${params.getLong("id") == it.id ? 'class="active"' : ''}>
                            <a href="${createLink(action: 'milestone',id: it.id)}">${it.title}<span class="label pull-right${it?.state == "closed" ? ' label-important">Closed' : it.dueOn?.before(new Date(System.currentTimeMillis())) ? ' label-warning">Overdue' : ' label-success">Open'}</span></a>
                            </li>
                        </g:each>
                    </ul>
                    <li><a href="${createLink(action: 'issue')}">General Issues</a></li>
                    <li><a href="${createLink(action: 'tagcloud')}">Tagcloud</a></li>
	            </ul>
	        </div><!--/.well -->
	    </div><!--/span-->
	 	<div class="span9">
            <ul class="nav nav-tabs">
                <li ${!params.get("id") && !selectedMilestone ? 'class="active"' : ''}>
                    <a href="${createLink(action: 'milestone')}">Open</a>
                </li>
                <li ${params.get("id")=="overdue" ? 'class="active"' : ''}>
                    <a href="${createLink(action: 'milestone', id: 'overdue')}">Overdue
                        ${selectedRepo.getMilestones().count {it.dueOn?.before(new Date(System.currentTimeMillis())) && it.state == "open"}.intValue() > 0 ? "<span class='badge badge-important'>"+selectedRepo.getMilestones().count {it.dueOn!= null && it.dueOn.before(new Date(System.currentTimeMillis())) && it.state == "open"}.intValue() + "</span>" : ""}</a>
                </li>
                <li ${params.get("id")=="closed" ? 'class="active"' : ''}>
                    <a href="${createLink(action: 'milestone', id: 'closed')}">Closed</a>
                </li>
                <li ${params.get("id")=="all" ? 'class="active"' : ''}>
                    <a href="${createLink(action: 'milestone', id: 'all')}">All</a>
                </li>
                    ${selectedMilestone != null ? '<li class="active">\n' +
                        '<a href="${createLink(action: \'milestone\', id: '+selectedMilestone.id+')}">'+selectedMilestone.title+'</a></li>' : ''}
            </ul>
            <g:if test="${selectedMilestone == null}">
                <div class="row" style="${selectedMilestone != null ? 'display: none;' : ''}">
                    <g:each in="${milestones}">
                        <g:formatMilestone milestone="${it}"/>
                    </g:each>
                </div>
            </g:if>
            <g:if test="${selectedMilestone != null}">
                <div class="row">
                    <div class="row-fluid">
                        <g:formatMilestone milestone="${selectedMilestone}"/>
                        <hr>
                        <div id="tagcloud" class="span12" style="height: 600px; margin-bottom: 100px;">
                            <g:each in="${teamTags}">
                                <span data-weight="${it.value}">${it.key}</span>
                            </g:each>
                        </div>
                    </div>
                    <g:javascript src="awesomecloud.js" />
                    <script>
                        var settings = {
                            "size" : {
                                "grid" : 4,
                                "normalize" : false
                            },
                            "options" : {
                                "color" : "random-dark",
                                "printMultiplier" : 2,
                                "sort" : "random"
                            },
                            "font" : "Futura, Helvetica, sans-serif",
                            "shape" : "square"
                        };
                        $("#tagcloud").awesomeCloud(settings);
                    </script>
                </g:if>
            </div>
    	</div><!--/span-->
    </div><!--/row-->
</body>
</html>