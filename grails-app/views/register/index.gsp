<html>

<head>
    <meta name='layout' content='register'/>
    <title><g:message code='spring.security.ui.register.title'/></title>
    <r:require modules="bootstrap"/>
</head>
<body>

<div class="container">
    <g:form class="form-signin form-horizontal" action='register' name='registerForm' method='POST' autocomplete='off'>
        <h2 class="form-signin-heading">
            <a href="${createLinkTo(dir: '')}" alt="Back" title="Back"><i class="mega-icon mega-icon-arr-left"></i></a>
            <g:message code='spring.security.ui.register.title'/>
        </h2>
        <fieldset>
            <g:if test='${emailSent}'>
                <div class="alert alert-success">
                    <g:message code='spring.security.ui.register.sent'/>
                </div>
            </g:if>
            <g:else>
                <s2ui:textFieldRow name='username' labelCode='user.username.label' bean="${command}"
                                   size='20' labelCodeDefault='Username' value="${command.username}"/>
                <s2ui:textFieldRow name='email' bean="${command}" value="${command.email}"
                                   size='20' labelCode='user.email.label' labelCodeDefault='E-mail'/>
                <s2ui:passwordFieldRow name='password' labelCode='user.password.label' bean="${command}"
                                       size='20' labelCodeDefault='Password' value="${command.password}"/>
                <s2ui:passwordFieldRow name='password2' labelCode='user.password2.label' bean="${command}"
                                       size='20' labelCodeDefault='Password (again)' value="${command.password2}"/>
                <div class="form-footer">
                    <s2ui:submitButtonWithReset elementId='create' form='registerForm'
                                                messageCode='spring.security.ui.register.submit'/>
                </div>
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
