<html>

<head>
    <title><g:message code='spring.security.ui.forgotPassword.title'/></title>
    <meta name='layout' content='register'/>
    <r:require modules="bootstrap"/>
</head>

<body>
<div class="container">
<g:form class="form-signin" action='forgotPassword' name='forgotPasswordForm' autocomplete='off'>
    <h2 class="form-signin-heading"><g:message code="spring.security.ui.forgotPassword.header" /></h2>
	<g:if test='${emailSent}'>
        <div class="alert alert-success">
	        <g:message code='spring.security.ui.forgotPassword.sent'/>
        </div>
	</g:if>

	<g:else>
	<p><g:message code='spring.security.ui.forgotPassword.description'/></p>

	<table>
		<tr>
			<td><label for="username"><g:message code='spring.security.ui.forgotPassword.username'/></label></td>
			<td><g:textField name="username" size="25" /></td>
		</tr>
	</table>

	<s2ui:submitButton class="btn btn-primary" elementId='reset' form='forgotPasswordForm' messageCode='spring.security.ui.forgotPassword.submit'/>

	</g:else>

	</g:form>
</div>

<script>
$(document).ready(function() {
	$('#username').focus();
});
</script>

</body>
</html>
