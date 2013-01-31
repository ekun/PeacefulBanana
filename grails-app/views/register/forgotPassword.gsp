<html>

<head>
    <title><g:message code='spring.security.ui.forgotPassword.title'/></title>
    <meta name='layout' content='register'/>
    <r:require modules="bootstrap"/>
</head>

<body>
<div class="container">
    <g:form class="form-signin form-horizontal" action='forgotPassword' name='forgotPasswordForm' autocomplete='off'>
        <h2 class="form-signin-heading"><g:message code="spring.security.ui.forgotPassword.header" /></h2>
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
