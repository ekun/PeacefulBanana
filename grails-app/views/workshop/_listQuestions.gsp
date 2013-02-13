<div class="accordion" id="accordion2" style="margin-bottom: 50px;">
    <div class="accordion-group">
        <div class="accordion-heading">
            <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapseOne">
                Mandatory questions
            </a>
        </div>
        <div id="collapseOne" class="accordion-body collapse in">
            <div class="accordion-inner">
                <ol>
                    <g:each in="${questions.subList(0,5)}">
                        <li style="padding: 15px 15px;">${it.questionText}</li>
                    </g:each>
                </ol>
            </div>
        </div>
    </div>
    <div class="accordion-group">
        <div class="accordion-heading">
            <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapseTwo">
                Questions generated from the most active tags
            </a>
        </div>
        <div id="collapseTwo" class="accordion-body collapse">
            <div class="accordion-inner">
                <ol start="6">
                    <g:if test="${questions.size() > 5}">
                        <g:each in="${questions.subList(6,questions.size())}">
                            <li style="padding: 15px 15px;">${it.questionText}<g:submitToRemote class="btn btn-mini btn-danger pull-right" action="ajaxDeleteQuestion" id="${it.id}" value="Remove" onComplete="reloadList();"></g:submitToRemote></li>
                        </g:each>
                    </g:if>
                </ol>
            </div>
        </div>
    </div>
    <div class="accordion-group">
        <div class="accordion-heading">
            <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapseThree">
                Possible tags questions
            </a>
        </div>
        <div id="collapseThree" class="accordion-body collapse">
            <div class="accordion-inner">
                <p>Click to generate a question based on the tag.</p>
                <g:each in="${tags.sort {a, b -> b.value <=> a.value}}">
                    <form method="post" style="display: inline-table; margin: 5px;">
                        <g:hiddenField name="tag" value="${it.key}" />
                        <g:submitToRemote class="btn" action="ajaxAddQuestion" id="${params.get("id")}" value="${it.key} (Referenced ${it.value} times)" onComplete="reloadList2();" />
                    </form>
                </g:each>
            </div>
        </div>
    </div>
</div>
