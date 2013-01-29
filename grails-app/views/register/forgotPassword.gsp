<html>

<head>
    <title><g:message code='spring.security.ui.forgotPassword.title'/></title>
    <meta name='layout' content='register'/>
    <style type="text/css">
    body {
        padding-top: 140px;
        padding-bottom: 40px;
        background-color: #f5f5f5;
    }

    .form-signin {
        max-width: 350px;
        padding: 19px 29px 29px;
        margin: 0 auto 20px;
        background-color: #fff;
        border: 1px solid #e5e5e5;
        -webkit-border-radius: 5px;
        -moz-border-radius: 5px;
        border-radius: 5px;
        -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
        -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
        box-shadow: 0 1px 2px rgba(0,0,0,.05);
    }
    .form-signin .form-signin-heading,
    .form-signin .checkbox {
        margin-bottom: 10px;
    }
    .form-signin input[type="text"],
    .form-signin input[type="password"] {
        font-size: 16px;
        height: auto;
        margin-bottom: 15px;
        padding: 7px 9px;
    }
    </style>
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
