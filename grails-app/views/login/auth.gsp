<html>
<head>
	<meta name='layout' content='main'/>
    <r:require modules="bootstrap"/>
	<title><g:message code="springSecurity.login.title"/></title>
</head>

<body>
<div id='login'>
	<div class='inner'>
        <div class='fheader'><g:message code="springSecurity.login.header"/></div>
        <g:if test='${flash.message}'>
            <div class="alert alert-error">
                ${flash.message}
            </div>
        </g:if>
        <form action='${postUrl}' method='POST' id='loginForm' class="form-horizontal" autocomplete='off'>
            <div class="control-group">
                <label class="control-label" for="username"><g:message code="springSecurity.login.username.label"/></label>
                <div class="controls">
                    <input type="text" id="username" name='j_username' placeholder="Username">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="password"><g:message code="springSecurity.login.password.label"/></label>
                <div class="controls">
                    <input type="password" name='j_password' id='password' placeholder="Password">
                </div>
            </div>
            <div class="control-group">
                <div class="controls">
                    <label class="checkbox">
                        <input type="checkbox" name='${rememberMeParameter}' id='remember_me' <g:if test='${hasCookie}'>checked='checked'</g:if>> <g:message code="springSecurity.login.remember.me.label"/>
                    </label>
                    <button type="submit" id="submit" class="btn">${message(code: "springSecurity.login.button")}</button>
                </div>
            </div>
        </form>
	</div>
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
