<html>
<head>
    <title><g:message code='spring.security.ui.resetPassword.title'/></title>
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
    <g:form class="form-signin" action='resetPassword' name='resetPasswordForm' autocomplete='off'>
        <h2 class="form-signin-heading"><g:message code="spring.security.ui.resetPassword.header" /></h2
	    <g:hiddenField name='t' value='${token}'/>
	    <h4><g:message code='spring.security.ui.resetPassword.description'/></h4>

        <table>
            <s2ui:passwordFieldRow name='password' labelCode='resetPasswordCommand.password.label' bean="${command}"
                                 labelCodeDefault='Password' value="${command?.password}"/>

            <s2ui:passwordFieldRow name='password2' labelCode='resetPasswordCommand.password2.label' bean="${command}"
                                 labelCodeDefault='Password (again)' value="${command?.password2}"/>
        </table>

	    <s2ui:submitButton elementId='reset' form='resetPasswordForm' messageCode='spring.security.ui.resetPassword.submit'/>
	</g:form>
</div>


<script>
$(document).ready(function() {
	$('#password').focus();
});
</script>

</body>
</html>
