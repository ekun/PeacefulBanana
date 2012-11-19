<%@ page import="org.peaceful.banana.gitdata.Milestone" %>
<div class='item'>
    <h3>#${milestone?.number} - ${milestone?.title}
        <div class='pull-right'>
            ${milestone?.state == "closed" ? '<span class="label label-important">Closed</span>' : '<span class="label label-success">Open</span>'}
        </div>
    </h3>
    <div class="progress">
        <div class="bar" style="width: ${milestone.closed.size() > 0 ? ((int)(milestone.closed.size() / (milestone.open.size()+milestone.closed.size()))*100) : 0}%;"></div>
    </div>
    <p>${milestone?.description}</p>
    <hr>
    <g:each in="${milestone.open}">
        <p>${it?.state == "closed" ? '<span class="label label-important">Closed</span>' : '<span class="label label-success">Open</span>'} <a href="${createLink(action: 'issue', id: it.githubId)}">#${it.number} - ${it.title}</a> </p>
    </g:each>
    <g:each in="${milestone.closed}">
        <p>${it?.state == "closed" ? '<span class="label label-important">Closed</span>' : '<span class="label label-success">Open</span>'} <a href="${createLink(action: 'issue', id: it.githubId)}">#${it.number} - ${it.title}</a> </p>
    </g:each>
</div>