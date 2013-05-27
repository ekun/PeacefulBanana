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
<html>
<head>
    <title><g:message code='spring.security.ui.forgotPassword.title'/></title>
    <meta name='layout' content='register'/>
    <r:require modules="bootstrap"/>
</head>

<body>
<div class="container">
    <g:form class="form-signin form-horizontal" action='forgotPassword' name='forgotPasswordForm' autocomplete='off'>
        <h2 class="form-signin-heading">
            <a href="${createLink(controller: 'login', action: 'auth')}"><i class="mega-icon mega-icon-arr-left"></i></a>
            <g:message code="spring.security.ui.forgotPassword.header" /></h2>
        <fieldset>
            <g:if test='${emailSent}'>
                <div class="alert alert-success">
                    <g:message code='spring.security.ui.forgotPassword.sent'/>
                </div>
            </g:if>

            <g:else>
                <p><g:message code='spring.security.ui.forgotPassword.description'/></p>
                <div class='control-group'>
                    <label class="control-label" for="username"><g:message code='spring.security.ui.forgotPassword.username'/></label>
                    <div class='controls'>
                        <g:textField name="username" size="25" />
                    </div>
                </div>

            <s2ui:submitButton elementId='reset' form='forgotPasswordForm' messageCode='spring.security.ui.forgotPassword.submit'/>

            </g:else>
        </fieldset>
	</g:form>
</div>

<script>
$(document).ready(function() {
	$('#username').focus();
});
</script>

</body>
</html>
