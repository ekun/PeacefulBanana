<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
<html>
    <head>
        <title><g:layoutTitle default="Peaceful Banana"/></title>
        <r:layoutResources/>
        <style type="text/css">
        body {
            padding-top: 60px;
            padding-bottom: 40px;
        }
        .sidebar-nav {
            padding: 9px 0;
        }
        </style>
        <link href="${createLinkTo(dir:'css')}/main.css" rel="stylesheet" type="text/css">
        <g:javascript library="application" />
        <gvisualization:apiImport/>
    </head>
    <body>
        <div class="navbar navbar-inverse navbar-fixed-top">
            <div class="navbar-inner">
                <div class="container-fluid">
                    <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </a>
                    <a class="brand" href="#">Peaceful Banana</a>
                    <div class="nav-collapse collapse">
                        <p class="navbar-text pull-right">
                            <sec:ifLoggedIn>
                                You are logged in as <sec:username/>. [<g:link controller='logout'>Log out</g:link>]
                            </sec:ifLoggedIn>
                            <sec:ifNotLoggedIn>
                                <g:link controller='login' action='auth'>Login</g:link>
                            </sec:ifNotLoggedIn>
                        </p>
                        <ul class="nav">
                            <li ${controllerName == null ? 'class="active"' : ''}><a href="${createLinkTo(dir:'')}"><i class="icon-home"></i> Home</a></li>
                            <li ${controllerName.equals('repositories') ? 'class="active"' : ''}><a href="${createLinkTo(dir:'repositories')}"><i class="icon-folder-open"></i> Repositories</a></li>
                            <li ${controllerName.equals('settings') ? 'class="active"' : ''}><a href="${createLinkTo(dir:'settings')}"><i class="icon-wrench"></i> Settings</a></li>
                        </ul>
                    </div><!--/.nav-collapse -->
                </div>
            </div>
        </div>
    <div class="container-fluid">
        <g:layoutBody/>
        <hr>

        <footer>
            <p>© The Plain Penguin Company 2012</p>
        </footer>

    </div>
        <r:layoutResources/>
    </body>
</html>
