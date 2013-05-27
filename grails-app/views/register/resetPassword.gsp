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
    <title><g:message code='spring.security.ui.resetPassword.title'/></title>
    <meta name='layout' content='register'/>
    <r:require modules="bootstrap"/>
</head>

<body>
<div class="container">
    <g:form class="form-signin form-horizontal" controller="register" action='resetPassword' method="POST" name='resetPasswordForm' autocomplete='off'>
        <h2 class="form-signin-heading">
            <g:message code="spring.security.ui.resetPassword.header" />
        </h2>
        <fieldset>
            <g:hiddenField name='t' value='${params.t}'/>
            <s2ui:passwordFieldRow name='password' labelCode='resetPasswordCommand.password.label' bean="${command}"
                                   labelCodeDefault='Password' value="${command?.password}"/>
            <s2ui:passwordFieldRow name='password2' labelCode='resetPasswordCommand.password2.label' bean="${command}"
                                   labelCodeDefault='Password (again)' value="${command?.password2}"/>
            <div class="form-footer">
            <s2ui:submitButton elementId='reset' form='resetPasswordForm' messageCode='spring.security.ui.resetPassword.submit'/>
            </div>
        </fieldset>
	</g:form>
</div>


<script>
$(document).ready(function() {
	$('#password').focus();
});
</script>

</body>
</html>
