<html>

<head>
    <meta name='layout' content='register'/>
    <title><g:message code='spring.security.ui.register.title'/></title>
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
    <g:form class="form-signin" action='register' name='registerForm' method='POST' autocomplete='off'>
        <h2 class="form-signin-heading">Register</h2>
            <g:if test='${emailSent}'>
                <div class="alert alert-success">
                    <g:message code='spring.security.ui.register.sent'/>
                </div>
            </g:if>
            <g:else>
                <table>
                    <tbody>
                    <s2ui:textFieldRow name='username' labelCode='user.username.label' bean="${command}"
                                       size='40' labelCodeDefault='Username' value="${command.username}"/>
                    <s2ui:textFieldRow name='email' bean="${command}" value="${command.email}"
                                       size='40' labelCode='user.email.label' labelCodeDefault='E-mail'/>
                    <s2ui:passwordFieldRow name='password' labelCode='user.password.label' bean="${command}"
                                           size='40' labelCodeDefault='Password' value="${command.password}"/>
                    <s2ui:passwordFieldRow name='password2' labelCode='user.password2.label' bean="${command}"
                                           size='40' labelCodeDefault='Password (again)' value="${command.password2}"/>
                    </tbody>
                </table>
                <s2ui:submitButton elementId='create' class="btn btn-primary" form='registerForm' messageCode='spring.security.ui.register.submit'/>
            </g:else>
        </g:form>
    </div>
</div>

<script>
    $(document).ready(function() {
        $('#username').focus();
    });
</script>

</body>
</html>
