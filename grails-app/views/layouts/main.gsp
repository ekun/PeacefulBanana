<%@ page import="org.peaceful.banana.TeamRole; org.peaceful.banana.Notification; org.peaceful.banana.User" %>
<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
<html>
    <head>
        <title>Peaceful Banana - <g:layoutTitle default="Home"/></title>
        <meta name="viewport" content="initial-scale = 1.0">

        <!-- Le HTML5 shim, for IE6-8 support of HTML elements -->
        <!--[if lt IE 9]>
			<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->

        <!-- Le fav and touch icons -->
        <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
        <link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">

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
        <g:layoutHead/>
        <r:layoutResources />
    </head>
    <body>
    <g:javascript>
        $.ajax({
            url: "${createLink(controller: 'githubSync', action: 'index')}",
            dataType:"json",
            async: true,
            success: function(data) {
                if(data.update) {
                    $("#gotUpdate").show();
                }
            }
        });

        function onSyncComplete() {
            $("#syncBtn").removeClass("btn-info");
            $("#syncBtn").addClass("btn-success");
            $("#syncBtn").attr("value", "Synced");
        }

        function onSyncing() {
            $("#syncBtn").removeClass("btn-primary");
            $("#syncBtn").addClass("btn-info");
            $("#syncBtn").attr("disabled", "disabled");
            $("#syncBtn").addClass("disabled");
            $("#syncBtn").attr("value", "Syncing...");
        }

    </g:javascript>
        <div class="navbar navbar-inverse navbar-fixed-top" style="z-index: 100001;">
            <div class="navbar-inner">
                <div class="container-fluid">
                    <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </a>
                    <a class="brand" href="${createLinkTo(dir:'')}"><img src="${createLinkTo(dir:'images')}/banana-logo.png" width="18px"> Peaceful Banana</a>
                    <div class="nav-collapse collapse">
                        <sec:ifLoggedIn>
                            <ul class="nav nav-pills pull-right">
                                <li class="divider-vertical"></li>
                                <li class="dropdown">
                                    <a class="dropdown-toggle"
                                       data-toggle="dropdown"
                                       href="#">
                                        <i class="icon-user"></i> <sec:username/>
                                        <b class="caret"></b>
                                    </a>
                                    <ul class="dropdown-menu" style="margin-top: 3px;">
                                        <!-- li1047/nks -->
                                        <li>
                                            <a href="${createLink(controller: 'reflection', action: 'mood')}"><i class="icon-thumbs-up"></i> Mood</a>
                                        </li>
                                        <li>
                                            <a href="${createLink(controller: 'reflection')}"><i class="icon-book"></i> Notes</a>
                                        </li>
                                        <li class="divider"></li>
                                        <li>
                                            <a href="${createLinkTo(dir:'settings')}">
                                                <i class="icon-wrench"></i>
                                                Settings
                                            </a>
                                        </li>
                                        <li class="divider"></li>
                                        <li><g:link controller='logout'><i class="mini-icon mini-icon-logout"></i> Log out</g:link></li>
                                    </ul>
                                </li>
                            </ul>
                            <ul class="nav nav-pills pull-right">
                                <li class="divider-vertical"></li>
                                <li class="dropdown">
                                    <a class="dropdown-toggle"
                                       data-toggle="dropdown"
                                       href="#">
                                        <i class="icon-inbox"></i>
                                        <g:if test="${Notification.findAllByUserAndUnreadAndCleared(User.findByUsername(sec.loggedInUserInfo(field:'username')), true, false).size() > 0}">
                                            <span class="badge badge-important">${Notification.findAllByUserAndUnreadAndCleared(User.findByUsername(sec.loggedInUserInfo(field:'username')),true,false).size()}</span>
                                        </g:if>
                                    </a>
                                    <ul class="dropdown-menu" style="width: 300px; margin-top: 3px;">
                                        <!-- Notifications -->
                                        <g:formatNotification notification="${User.findByUsername(sec.loggedInUserInfo(field:'username')).getNotifications()}" />
                                        <!-- END Notifications -->
                                        <li class="divider"></li>
                                        <li>
                                            <center>
                                                <a href="${createLink(controller: 'notification', action: 'center')}"><i class="icon-inbox"></i> Notification Center</a>
                                            </center>
                                        </li> <!-- TODO: Style and link to some notification center -->
                                    </ul>
                                </li>
                            </ul>
                        </sec:ifLoggedIn>
                        <sec:ifNotLoggedIn>
                            <ul class="nav pull-right">
                                <li><g:link controller='login' action='auth'>Login</g:link></li>
                            </ul>
                        </sec:ifNotLoggedIn>
                        <sec:ifLoggedIn>
                        <p class="nav pull-right" id="gotUpdate" style="padding-right: 15px; display: none;">
                                <g:submitToRemote id="syncBtn" class="btn btn-primary" controller="githubSync" action="sync"
                                              update="[success: 'message', failure: 'error']" onComplete="onSyncComplete()" onLoading="onSyncing()" value="Sync.."/>
                        </p>
                        </sec:ifLoggedIn>
                        <div id="spinner" class="nav spinner pull-right" style="padding: 8px;">
                            <img src="${resource(dir: 'images', file: 'spinner.gif')}" alt="Spinner"/>
                        </div>
                        <ul class="nav">
                            <li ${controllerName == null ? 'class="active"' : ''}><a href="${createLinkTo(dir:'')}"><i class="icon-home"></i> Home</a></li>
                            <sec:ifLoggedIn>
                                <li ${controllerName.equals('repositories') ? 'class="active"' : ''}>
                                    <a href="${createLink(controller: 'repositories')}">
                                        <i class="icon-folder-open"></i>
                                        Repository
                                    </a>
                                </li>
                                <li ${controllerName.equals('reflection') ? 'class="active"' : ''}>
                                    <a href="${createLink(controller: 'reflection')}">
                                        <i class="icon-book"></i>
                                        Reflection
                                    </a>
                                </li>
                                <li ${controllerName.equals('team') ? 'class="active"' : ''}>
                                    <a href="${createLink(controller:'team')}">
                                        <i class="icon-globe"></i>
                                        Team
                                    </a>
                                </li>
                                <g:if test="${User.findByUsername(sec.loggedInUserInfo(field:'username')).teamRole() == TeamRole.MANAGER ||
                                        User.findByUsername(sec.loggedInUserInfo(field:'username'))?.activeTeam()?.owner == User.findByUsername(sec.loggedInUserInfo(field:'username'))}">
                                    <li class="divider-vertical"></li>
                                    <li ${controllerName.equals('workshop') ? 'class="active"' : ''}>
                                        <a href="${createLink(controller: 'workshop')}" title="Manager status required" alt="Manager status required">
                                            <i class="icon-briefcase"></i>
                                            <font color=orange> Workshop</font>
                                        </a>
                                    </li>
                                </g:if>
                            </sec:ifLoggedIn>
                        </ul>
                    </div><!--/.nav-collapse -->
                </div>
            </div>
        </div>
    <div class="container-fluid">
        <g:if test="${params.get('error')}">
            <div class="alert alert-error">
                ${params.get('error')}
            </div>
        </g:if>
        <g:layoutBody/>
    </div>
    <div id="footer">
        <div class="container">
            <p class="muted credit">© The Plain Penguin Company 2012</p>
        </div>
    </div>
        <r:layoutResources/>
    </body>
</html>