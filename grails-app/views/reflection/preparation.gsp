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
                <li><a href="${createLink(action: 'index')}">Notes</a></li>
                <li class="active"><a href="${createLink(action: 'preparation')}">Workshop preparation</a>
                    <ul class="nav nav-list">
                        <li><a href="#note">Notes</a></li>
                        <li><a href="#mood">Mood</a></li>
                    </ul>
                </li>

            </ul>
        </div><!--/.well -->
    </div><!--/span-->
    <div class="span9">
        <h1 style="margin-bottom: 30px;">Preparation</h1>
        <a id="note"></a>
        <p>Under can you see your notes created for the periode</p>
        <g:form class="input-append" action="preparation" method="POST">
            <g:select class="input-xlarge" name="workshop" from="${workshops}" optionKey="id" value="${params.getLong("workshop")}"
                      optionValue="${{it.dateStart.format("EEE, d MMM yyyy") + " -> " + it.dateEnd.format("EEE, d MMM yyyy")}}" noSelection="[null:'All time']" />
            <button type="submit" class="btn btn-primary">Reload</button>
        </g:form>
        <hr>
        <div class="row">
            <div class="span5" style="margin-left: 30px;">
                <h3>Contributions</h3>
                <g:each in="${notes}">
                    <div class="comment">
                        <div class="comment-bubble bubble">
                            <div class="comment-inner">
                                <div class="commit-header">
                                    <span class="comment-header-text"><i class="mini-icon mini-icon-reference"></i> ${it.shared ? 'Shared' : 'Private'}</span>
                                    <span class="comment-header-time pull-right">
                                        <joda:time value="${new DateTime(it.dateCreated)}">
                                            <joda:format value="${it}" pattern="yyyy-MM-dd HH:mm:ss" />
                                        </joda:time>
                                    </span>
                                </div>
                                <div class="commit-content">${StringEscapeUtils.unescapeHtml(it.contributions)}</div>
                            </div>
                        </div>
                    </div>
                </g:each>
            </div>
            <div class="span5 offset1">
                <h3>Improvements</h3>
                <g:each in="${notes}">
                    <div class="comment">
                        <div class="comment-bubble bubble">
                            <div class="comment-inner">
                                <div class="commit-header">
                                    <span class="comment-header-text"><i class="mini-icon mini-icon-reference"></i> ${it.shared ? 'Shared' : 'Private'}</span>
                                    <span class="comment-header-time pull-right">
                                        <joda:time value="${new DateTime(it.dateCreated)}">
                                            <joda:format value="${it}" pattern="yyyy-MM-dd HH:mm:ss" />
                                        </joda:time>
                                    </span>
                                </div>
                                <div class="commit-content">${StringEscapeUtils.unescapeHtml(it.improvements)}</div>
                            </div>
                        </div>
                    </div>
                </g:each>
            </div>
        </div>
        <div class="row">
            <a id="mood"></a>
            <div id='chart_div' style='width: 700px; height: 480px;'></div>
        </div>
    </div><!--/span-->
</div><!--/row-->
<g:javascript>
    google.load('visualization', '1', {'packages':["corechart"]});
    google.setOnLoadCallback(drawChart);
    function drawChart() {
        var jsonData = $.ajax({
            url: "${createLink(controller: 'reflectionData', action: 'mood', params: dateParams)}",
            dataType:"json",
            async: true,
            success: function(jsonData) {
                // Create our data table out of JSON data loaded from server.
                var data = new google.visualization.DataTable(jsonData);

                var options = {
                    title: 'Mood Graph',
                    vAxis: {maxValue: 100, minValue: 0}
                };

                // Instantiate and draw our chart, passing in some options.
                var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
                chart.draw(data, options);
            }
        }).responseText;
    }
</g:javascript>
</body>
</html>