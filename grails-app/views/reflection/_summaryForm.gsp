<hr>
<h3>Summary</h3>
<form class="form-horizontal" action="${createLink(action: 'summary')}" method='POST' autocomplete='off'>
    <div class="control-group ${hasErrors(bean:note,field:'mood','error')}">
        <label class="control-label" for="moodSelector">Mood</label>
        <div class="controls">
            <select class="span3" id="moodSelector" name="moodSelector">
                <option value="1">Very sad</option>
                <option value="25">Sad</option>
                <option value="50" selected="">Neutral</option>
                <option value="75">Happy</option>
                <option value="100">Very happy</option>
            </select>
            <span class="help-inline">Select your mood</span>
        </div>
    </div>
    <div class="control-group ${hasErrors(bean:note,field:'contributions','error')}">
        <label class="control-label" for="contributions">Top 2 contributions</label>
        <div class="controls">
            <textarea rows="6" class="span5" id="contributions" name="contributions">${note?.errors?.getFieldValue("contributions")}</textarea>
            <span class="help-inline"><g:hasErrors bean="${note}"><g:message error="${note?.errors?.getFieldError("contributions")}" /></g:hasErrors></span>
        </div>
    </div>
    <div class="control-group ${hasErrors(bean:note,field:'improvements','error')}">
        <label class="control-label" for="improvements">Top 2 improvements</label>
        <div class="controls">
            <textarea rows="6" class="span5" id="improvements" name="improvements">${note?.errors?.getFieldValue("improvements")}</textarea>
            <span class="help-inline"><g:hasErrors bean="${note}"><g:message message="${note?.errors?.getFieldError("improvements")}" /></g:hasErrors></span>
        </div>
    </div>
    <div class="control-group">
        <div class="controls">
            <button type="submit" class="btn btn-large btn-primary">Submit</button>
        </div>
    </div>
</form>
