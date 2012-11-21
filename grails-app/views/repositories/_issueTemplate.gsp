<div class='item' style="${issue == null ? 'display: none;' : ''}">
    <h3>#${issue?.number} ${issue?.title}
        <div class='pull-right'>
        ${issue?.state == "closed" ? '<span class="label label-important">Closed</span>' : '<span class="label label-success">Open</span>'}
        </div>
    </h3>
    <p>${issue?.body}</p>
</div>
<div class="row-fluid">
    <div class="span8">
        <g:each in="${issue?.comments}">
            <div class="item">
                ${it.login + " <br> " + it.body}
            </div>
        </g:each>
    </div>
    <div class="span4">
        <g:each in="${issue?.events}">
            <div class="item">
                ${it.event + " - " + it.login}
            </div>
        </g:each>
    </div>
</div>
