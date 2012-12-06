<hr>
<h3>Summary</h3>
<form class="form-horizontal" action="${createLink(action: 'summary', id: 'save')}" method='POST' autocomplete='off'>
    <div class="control-group">
        <label class="control-label" for="moodSelector">Mood</label>
        <div class="controls">
            <select class="span3" id="moodSelector" name="moodSelector">
                <option value="1">Very sad</option>
                <option value="25">Sad</option>
                <option value="50">Neutral</option>
                <option value="75">Happy</option>
                <option value="100">Very happy</option>
            </select>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label" for="contributions">Top 2 contributions</label>
        <div class="controls">
            <textarea rows="6" class="span5" id="contributions" name="contributions"></textarea>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label" for="improvements">Top 2 improvements</label>
        <div class="controls">
            <textarea rows="6" class="span5" id="improvements" name="improvements"></textarea>
        </div>
    </div>
    <div class="control-group">
        <div class="controls">
            <button type="submit" class="btn btn-large btn-primary">Submit</button>
        </div>
    </div>
</form>