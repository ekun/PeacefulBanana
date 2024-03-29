<!--

    This file is part of Peaceful Banana.

    Peaceful Banana is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Peaceful Banana is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Peaceful Banana.  If not, see <http://www.gnu.org/licenses/>.

-->
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Github settings</title>
    <r:require modules="bootstrap"/>
</head>
<body>
	<div class="row-fluid">
	    <div class="span3">
	        <div class="well sidebar-nav">
	            <ul class="nav nav-list">
	                <li class="nav-header">Menu</li>
	                <li ${actionName.equals('index') ? 'class="active"' : ''}>
                        <a href="${createLinkTo(dir:'settings')}"><i class="mini-icon mini-icon-account-settings"></i> <sec:username/></a>
                    </li>
	                <li ${actionName.equals('github') ? 'class="active"' : ''}>
                        <a href="${createLinkTo(dir:'settings')}/github"><i class="mini-icon mini-icon-blacktocat"></i> GitHub</a>
                    </li>
	                <!-- <li ${actionName.equals('settings') ? 'class="active"' : ''}><a href="#"><i class="icon-wrench"></i> Settings</a></li> -->
	            </ul>
	        </div><!--/.well -->
	    </div><!--/span-->
	 	<div class="span9">
  			<h1>Github</h1>
            <g:if test="${gitUser == null}">
                <p>We were unable to find your session with github, please click the link below.</p>
                <g:githubOAuth/>
            </g:if>
            <g:else>
                <g:if test="${user?.activeTeam()}">
                <p>
                    <g:form name="changeRepo" action="changeSelectedRepo" class="form-inline">
                        <g:submitToRemote class="btn btn-danger" action="changeSelectedRepo" id="changeRepoButton"
                                      update="[success: 'message', failure: 'error']" onLoading="loading()" onSuccess="success()" onFailure="failure()" value="Force Github synchronization" />
                    </g:form>
                </p>
                </g:if>
                <g:else>
                    <p>You need to select a active team inorder to sync with Github.</p>
                </g:else>
            </g:else>
        <div id="message"></div>
        <div id="error"></div>
    </div><!--/span-->
    </div><!--/row-->
<g:javascript>
    function success() {
        $("#changeRepoButton").removeClass("btn-primary");
        $("#changeRepoButton").addClass("btn-success");
        $("#changeRepoButton").attr("value", "Synced");
    }

    function failure() {
        $("#changeRepoButton").removeClass("btn-primary");
        $("#changeRepoButton").addClass("btn-danger");
        $("#changeRepoButton").attr("value", "Error..");
    }

    function loading() {
        $("#changeRepoButton").attr("disabled", "disabled");
        $("#changeRepoButton").addClass("disabled");
        $("#changeRepoButton").attr("value", "Syncing..");
    }

    function resetButton() {
        $("#changeRepoButton").removeClass("btn-success");
        $("#changeRepoButton").addClass("btn-primary");
        $("#changeRepoButton").attr("value", "Force Github synchronization");
    }
</g:javascript>
</body>
</html>