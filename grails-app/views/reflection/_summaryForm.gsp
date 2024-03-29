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
<hr>
<h3>Summary</h3>
<form class="form-horizontal" action="${createLink(action: 'summary')}" method='POST' autocomplete='off'>
    <div class="control-group ${hasErrors(bean:note,field:'mood','error')}">
        <label class="control-label" for="moodSelector">Your mood</label>
        <div class="controls">
            <select class="span3" id="moodSelector" name="moodSelector" required>
                <option value="1">Very sad</option>
                <option value="25">Sad</option>
                <option value="50" selected="">Neutral</option>
                <option value="75">Happy</option>
                <option value="100">Very happy</option>
            </select>
            <span class="help-block">How did you feel about todays work?</span>
        </div>
    </div>
    <div class="control-group ${hasErrors(bean:note,field:'contributions','error')}">
        <label class="control-label" for="contributions">Top two contributions for you and two for your the team.</label>
        <div class="controls">
            <textarea rows="6" class="span5" id="contributions" name="contributions" required>${note?.errors?.getFieldValue("contributions")}</textarea>
            <span class="help-block">
                Your contributions to this particular project, a contribution could be implementation of an feature etc that you are sattisfied with.
                <g:hasErrors bean="${note}"><g:message error="${note?.errors?.getFieldError("contributions")}" /></g:hasErrors>
            </span>
        </div>
    </div>
    <div class="control-group ${hasErrors(bean:note,field:'improvements','error')}">
        <label class="control-label" for="improvements">Top two improvements you personaly and two for your the team.</label>
        <div class="controls">
            <textarea rows="6" class="span5" id="improvements" name="improvements" required>${note?.errors?.getFieldValue("improvements")}</textarea>
            <span class="help-block">
                What you think the team or youself can improve for this particular project.
                <g:hasErrors bean="${note}"><g:message message="${note?.errors?.getFieldError("improvements")}" /></g:hasErrors>
            </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label" for="warning">Warning</label>
        <div class="controls">
            <label class="checkbox">
                <input type="checkbox" id="warning" required> Are you sure you want to submitt your note now?
            </label>
            <span class="help-block">You can only submitt one note per team per day.</span>
        </div>
    </div>
    <div class="control-group">
        <div class="controls">
            <button type="submit" class="btn btn-large btn-primary">Submit</button>
        </div>
    </div>
</form>
