<%@ page import="org.peaceful.banana.gitdata.Commit; org.peaceful.banana.gitdata.Issue; org.peaceful.banana.gitdata.Milestone; org.peaceful.banana.gitdata.Repository" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Admin - Github</title>
    <r:require modules="bootstrap"/>
</head>
<body>
<div class="row-fluid">
    <div class="span3">
        <div class="well sidebar-nav">
            <ul class="nav nav-list">
                <li class="nav-header">Administration</li>
                <li><a href="${createLink(action: 'index')}">Dashboard</a></li>
                <li><a href="${createLink(action: 'users')}">Users</a></li>
                <li><a href="${createLink(action: 'notes')}">Notes</a></li>
                <li class="active"><a href="${createLink(action: 'github')}">Github</a></li>
            </ul>
        </div><!--/.well -->
    </div><!--/span-->
    <div class="span9">
        <h3>Github</h3>
        <table class="table table-striped">
            <caption>This table contains statistics about github data.</caption>
            <thead>
                <tr>
                    <th>Type</th>
                    <th>Total</th>
                    <th>Average per repo</th>
                    <th>Average per milestone</th>
                </tr>
            </thead>
            <tbody>
            <tr>
                <td>Repository</td>
                <td>${Repository.count()}</td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td>Milestone</td>
                <td>${Milestone.count()}</td>
                <td>${(Milestone.count() / Repository.count())}</td>
                <td></td>
            </tr>
            <tr>
                <td>Issue</td>
                <td>${Issue.count()}</td>
                <td>${(Issue.count() / Repository.count())}</td>
                <td>${(Issue.countByMilestoneNumberIsNotNull() / Milestone.count())}</td>
            </tr>
            <tr>
                <td>Commit</td>
                <td>${Commit.count()}</td>
                <td>${(Commit.count() / Repository.count())}</td>
                <td></td>
            </tr>
            </tbody>
        </table>
    </div><!--/span-->
</div><!--/row-->
</body>
</html>