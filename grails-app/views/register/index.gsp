<html>

<head>
    <meta name='layout' content='register'/>
    <title><g:message code='spring.security.ui.register.title'/></title>
    <r:require modules="bootstrap"/>
</head>
<body>

<div class="container">
    <g:form class="form-signin form-horizontal" action='register' name='registerForm' method='POST' autocomplete='off'>
        <h2 class="form-signin-heading">Register</h2>
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
                    <button class="btn btn-large btn-primary" type="submit" id="create"><g:message code="spring.security.ui.register.submit" /> </button>
                    <a class="btn btn-large pull-right" href="${createLinkTo(dir: '')}">Cancel</a>
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
