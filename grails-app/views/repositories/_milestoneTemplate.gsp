<%@ page import="org.joda.time.Duration; org.peaceful.banana.gitdata.Milestone" %>
<div class='item ${milestone?.dueOn != null && milestone?.state != "closed" ? 0 > milestone?.dueOn.compareTo(new Date(System.currentTimeMillis())) ? 'striped' : 'no-striped' : ''}'>
    <div class="due-time">
        ${milestone?.formatedDueDate()}.
        ${milestone?.dueOn != null && milestone?.state == "closed" ? milestone?.closed.count { it.closed.after(milestone?.dueOn) }.intValue() > 0 ? ' <span class="label label-important">Deadline not met</span>' : ' <span class="label label-success">Deadline met</span>' : '' }
    </div>
    <h3>#${milestone?.number} - ${milestone?.title}
        <div class='pull-right'>
            ${milestone?.state == "closed" ? '<span class="label label-important">Closed</span>' : '<span class="label label-success">Open</span>'}
        </div>
    </h3>
    <div class="progress${milestone?.dueOn != null ? 0 > milestone?.dueOn.compareTo(new Date(System.currentTimeMillis())) && milestone?.state == "open" ? ' progress-danger' : ' progress-success' : milestone.state == "closed" ? ' progress-success' : ''}">
        <div class="bar" style="width: ${milestone.closed.size() > 0 ? ((int)(milestone.closed.size() / (milestone.open.size()+milestone.closed.size()))*100) : 0}%;"></div>
    </div>
    <p>${milestone?.description}</p>
    <br>
    <g:each in="${milestone.open}">
        <p>${it?.state == "closed" ? '<span class="label label-important">Closed</span>' : '<span class="label label-success">Open</span>'}
            <a href="${createLink(action: 'issue', id: it.githubId)}">#${it.number} - ${it.title}</a>
        </p>
    </g:each>
    <g:each in="${milestone.closed}">
        <p>${it?.state == "closed" ? '<span class="label label-important">Closed</span>' : '<span class="label label-success">Open</span>'}
            <a href="${createLink(action: 'issue', id: it.githubId)}">#${it.number} - ${it.title}</a>
        </p>
    </g:each>
</div>