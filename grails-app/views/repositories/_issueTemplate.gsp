<div class='item'>
    <h3>#${issue?.number} ${issue?.title}
        <div class='pull-right'>
        ${issue?.state == "closed" ? '<span class="label label-important">Closed</span>' : '<span class="label label-success">Open</span>'}
        </div>
    </h3>
    <p>${issue?.body}</p>
</div>