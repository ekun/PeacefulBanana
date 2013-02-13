<div class='item' style="${issue == null ? 'display: none;' : ''}">
    <h3><a href="${createLink(action: 'issue', id: issue?.githubId)}">#${issue?.number} ${issue?.title}</a>
        <div class='pull-right'>
        ${issue?.state == "closed" ? '<span class="label label-important">Closed</span>' : '<span class="label label-success">Open</span>'}
        </div>
    </h3>
    <p>${issue?.body}</p>
</div>
