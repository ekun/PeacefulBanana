<ol style="padding-bottom: 100px;">
    <g:each in="${questions}">
        <li style="padding: 15px 15px;">${it.questionText}<g:submitToRemote class="btn btn-mini btn-danger pull-right" action="ajaxDeleteQuestion" id="${it.id}" value="Delete" onComplete="reloadList();"></g:submitToRemote></li>
    </g:each>
</ol>