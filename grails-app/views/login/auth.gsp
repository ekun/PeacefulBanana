<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>

    <title>
        <g:message code="springSecurity.login.title"/>
    </title>
    <style type="text/css">
    body {
        padding-top: 40px;
        padding-bottom: 40px;
        background-color: #f5f5f5;
    }

    .form-signin {
        max-width: 300px;
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
    <form class="form-signin" action='${postUrl}' method='POST' id='loginForm' autocomplete='off'>
        <h2 class="form-signin-heading">Please sign in</h2>
        <g:if test='${flash.message}'>
            <div class="alert alert-error">${flash.message}</div>
        </g:if>
        <input type="text" class="input-block-level" name='j_username' id='username' placeholder="<g:message code="springSecurity.login.username.label"/>">
        <input type="password" class="input-block-level" name='j_password' id='password' placeholder="<g:message code="springSecurity.login.password.label"/>">
        <label class="checkbox">
            <input type="checkbox" value="remember-me" name='${rememberMeParameter}' id='remember_me' <g:if test='${hasCookie}'>checked='checked'</g:if>> <g:message code="springSecurity.login.remember.me.label"/>
        </label>
        <button class="btn btn-large btn-primary" type="submit">${message(code: "springSecurity.login.button")}</button>
    </form>
</div>
<script type='text/javascript'>
    <!--
    (function() {
        document.forms['loginForm'].elements['j_username'].focus();
    })();
    // -->
</script>
</body>
</html>