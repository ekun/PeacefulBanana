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
<div class='item' style="${issue == null ? 'display: none;' : ''}">
    <h3>
        #${issue?.number} ${issue?.title}
        <div class='pull-right'>${issue?.state == "closed" ? '<span class="label label-important">Closed</span>' : '<span class="label label-success">Open</span>'}</div>
    </h3>
    <p>${issue?.body}</p>
</div>
<div class="row-fluid" style="${issue == null ? 'display: none;' : ''}">
    <div class="span4">
        <h3>Comments</h3>
        <g:each in="${issue?.comments}">
            <div class="comment">
                <div class="comment-bubble bubble">
                    <div class="comment-inner">
                        <div class="comment-header">
                            <span class="comment-header-text"><i class="mini-icon mini-icon-reference"></i> ${it.login} commented</span>
                            <span class="comment-header-time pull-right"><joda:formatPeriod value="${it.period}" fields="days,hours,minutes" /> ago</span>
                        </div>
                        <div class="comment-content">${it.body}</div>
                    </div>
                </div>
            </div>
        </g:each>
    </div>
    <div class="span4">
        <h3>Commit references</h3>
        <g:each in="${issue?.commits}">
            <div class="comment">
                <div class="comment-bubble bubble">
                    <div class="comment-inner">
                        <div class="commit-header">
                            <span class="comment-header-text"><i class="mini-icon mini-icon-reference"></i> ${it.login} commited</span>
                            <span class="comment-header-time pull-right"><joda:formatPeriod value="${it.period}" fields="days,hours,minutes" /> ago</span>
                        </div>
                        <div class="commit-content">${it.message}</div>
                    </div>
                </div>
            </div>
        </g:each>
    </div>
    <div class="span4">
        <h3>Events</h3>
        <g:each in="${issue?.events}">
            <div class="comment">
                <div class="comment-bubble bubble">
                    <div class="comment-inner">
                        <div class="commit-header">
                            <span class="comment-header-text">${it.event == "closed" ? '<span class="label label-important">'+it.event+'</span>' : '<span class="label label-success">'+it.event+'</span>' }
                                by ${it.login}
                            </span>
                            <span class="comment-header-time pull-right"> <small class="pull-right"><joda:formatPeriod value="${it.period}" fields="days,hours,minutes" /> ago</small></span>
                        </div>
                    </div>
                </div>
            </div>
        </g:each>
    </div>
</div>
