package org.peaceful.banana

class SecurityUiTagLib extends grails.plugins.springsecurity.ui.SecurityUiTagLib{

    def checkboxRow = { attrs ->

        String labelCodeDefault = attrs.remove('labelCodeDefault')
        String name = getRequiredAttribute(attrs, 'name', 'checkboxRow')
        String labelCode = getRequiredAttribute(attrs, 'labelCode', 'checkboxRow')
        def value = getRequiredAttribute(attrs, 'value', 'checkboxRow')
        def bean = getRequiredAttribute(attrs, 'bean', 'checkboxRow')

        def fieldAttributes = [name: name, value: value] + attrs
        out << """
		<tr class="prop">
			<td valign="top" class="name">
			<label for="${name}">${message(code: labelCode, default: labelCodeDefault)}</label>
			</td>
			<td valign="top" class="value ${hasErrors(bean: bean, field: name, 'errors')}">
			${checkBox(fieldAttributes)}
			${fieldErrors(bean: bean, field: name)}
			</td>
		</tr>
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
			    <span>
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
		<tr class="prop">
			<td valign="top" class="name">
			<label for="${name}">${message(code: labelCode, default: labelCodeDefault)}</label>
			</td>
			<td valign="top" class="value ${hasErrors(bean: bean, field: name, 'errors')}">
			${passwordField(fieldAttributes)}
			${fieldErrors(bean: bean, field: name)}
			</td>
		</tr>
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
		<tr class="prop">
			<td valign="top" class="name">
			<label for="${name}">${message(code: labelCode, default: labelCodeDefault)}</label>
			</td>
			<td valign="top" class="value ${hasErrors(bean: bean, field: name, 'errors')}">
			${textField(fieldAttributes)}
			${fieldErrors(bean: bean, field: name)}
			</td>
		</tr>
		"""

        writeDocumentReady out, "\$('#${name}').date_input();"
    }
}
