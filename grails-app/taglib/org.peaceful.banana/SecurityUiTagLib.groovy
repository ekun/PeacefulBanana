package org.peaceful.banana

/**
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
 */

class SecurityUiTagLib extends grails.plugins.springsecurity.ui.SecurityUiTagLib {

    def checkboxRow = { attrs ->

        String labelCodeDefault = attrs.remove('labelCodeDefault')
        String name = getRequiredAttribute(attrs, 'name', 'checkboxRow')
        String labelCode = getRequiredAttribute(attrs, 'labelCode', 'checkboxRow')
        def value = getRequiredAttribute(attrs, 'value', 'checkboxRow')
        def bean = getRequiredAttribute(attrs, 'bean', 'checkboxRow')

        def fieldAttributes = [name: name, value: value] + attrs
        out << """
        <div class='control-group${hasErrors(bean: bean, field: name, ' error')}'>
            <div class='controls'>
                <label class="control-label" for="${name}">
                    ${checkBox(fieldAttributes)} ${message(code: labelCode, default: labelCodeDefault)}
                </label>
                <span class="help-inline">
                    ${fieldErrors(bean: bean, field: name)}
			    </span>
            </div>
		</div>
		"""
    }

    def textFieldRow = { attrs ->
        String labelCodeDefault = attrs.remove('labelCodeDefault')
        String name = getRequiredAttribute(attrs, 'name', 'textFieldRow')
        String labelCode = getRequiredAttribute(attrs, 'labelCode', 'textFieldRow')
        def value = getRequiredAttribute(attrs, 'value', 'textFieldRow')
        def bean = getRequiredAttribute(attrs, 'bean', 'textFieldRow')

        def fieldAttributes = [name: name, value: value] + attrs
        out << """
        <div class='control-group${hasErrors(bean: bean, field: name, ' error')}'>
            <label class="control-label" for="${name}">${message(code: labelCode, default: labelCodeDefault)}</label>
            <div class='controls'>
                ${textField(fieldAttributes)}
                <span class="help-inline">
                    ${fieldErrors(bean: bean, field: name)}
			    </span>
            </div>
		</div>
		"""
    }

    def passwordFieldRow = { attrs ->
        String labelCodeDefault = attrs.remove('labelCodeDefault')
        String name = getRequiredAttribute(attrs, 'name', 'passwordFieldRow')
        String labelCode = getRequiredAttribute(attrs, 'labelCode', 'passwordFieldRow')
        def value = getRequiredAttribute(attrs, 'value', 'passwordFieldRow')
        def bean = getRequiredAttribute(attrs, 'bean', 'passwordFieldRow')

        def fieldAttributes = [name: name, value: value] + attrs
        out << """
        <div class='control-group${hasErrors(bean: bean, field: name, ' error')}'>
            <label class="control-label" for="${name}">${message(code: labelCode, default: labelCodeDefault)}</label>
            <div class='controls'>
                ${passwordField(fieldAttributes)}
                <span class="help-inline">
                    ${fieldErrors(bean: bean, field: name)}
			    </span>
            </div>
		</div>
		"""
    }

    def dateFieldRow = { attrs ->
        String labelCodeDefault = attrs.remove('labelCodeDefault')
        String name = getRequiredAttribute(attrs, 'name', 'dateFieldRow')
        String labelCode = getRequiredAttribute(attrs, 'labelCode', 'dateFieldRow')
        def value = getRequiredAttribute(attrs, 'value', 'dateFieldRow')
        def bean = getRequiredAttribute(attrs, 'bean', 'dateFieldRow')

        value = formatDate(date: value)

        def fieldAttributes = [name: name, value: value, maxlength: '20', 'class': 'date_input'] + attrs
        out << """
        <div class='control-group${hasErrors(bean: bean, field: name, ' error')}'>
            <label class="control-label" for="${name}">${message(code: labelCode, default: labelCodeDefault)}</label>
            <div class='controls'>
                ${textField(fieldAttributes)}
                <span class="help-inline">
                    ${fieldErrors(bean: bean, field: name)}
			    </span>
            </div>
		</div>
		"""

        writeDocumentReady out, "\$('#${name}').date_input();"
    }

    def submitButton = { attrs ->
        String form = getRequiredAttribute(attrs, 'form', 'submitButton')
        String elementId = getRequiredAttribute(attrs, 'elementId', 'submitButton')
        String text = resolveText(attrs)

        def writer = getOut()
        //writer << """<a id="${elementId}" """
        //writeRemainingAttributes writer, attrs
        //writer << "></a>\n"
        writer << "<button type='submit' id='${elementId}_submit' class='btn btn-large btn-primary'>${text}</button>"

        String javascript = """
		"""
        writeDocumentReady writer, javascript
    }

    def submitButtonWithReset = { attrs ->
        String form = getRequiredAttribute(attrs, 'form', 'submitButton')
        String elementId = getRequiredAttribute(attrs, 'elementId', 'submitButton')
        String text = resolveText(attrs)

        def writer = getOut()

        writer << "<button type='submit' id='${elementId}_submit' class='btn btn-large btn-primary'>${text}</button>"
        writer << "<button type='reset' id='${elementId}_reset' class='btn btn-large pull-right'>Reset</button>"

        String javascript = """
		"""
        writeDocumentReady writer, javascript
    }
}
