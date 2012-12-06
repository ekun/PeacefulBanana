<html>
<head>
    <meta name="layout" content="main"/>
    <title>Reflection Center</title>
    <r:require modules="bootstrap"/>
</head>
<body>
<div class="row-fluid">
    <div class="span3">
        <div class="well sidebar-nav">
            <ul class="nav nav-list">
                <li class="nav-header">Reflection</li>
                <li class="active"><a href="${createLink(action: 'index')}">Notes</a></li>
                <li><a href="${createLink(action: 'summary')}">Summary</a></li>
            </ul>
        </div><!--/.well -->
    </div><!--/span-->
    <div class="span9">
        <h3>Notes</h3>
        <div id="feedback"></div>
        <g:if test="${!params.id}">
            <table class="table table-striped">
                <thead>
                <tr>
                    <g:sortableColumn property="id" title="#" />
                    <g:sortableColumn property="dateCreated" title="Created" />
                    <th></th>
                </tr>
                </thead>
                <tbody id="target">
                <g:formatNotesList notes="${notes}"/>
                </tbody>
            </table>
            <center><g:paginate controller="reflection" maxsteps="5" action="index" total="${notesCount}"/></center>
        </g:if>
        <g:if test="${params.id}">
            ${params.id && !selected ? '<div class="alert alert-error">\n' +
                    '<strong>Error</strong> Invalid id.' +
                    '</div>' : ''}
            <g:formatNotesLarge notification="${selected}" />
        </g:if>

    </div><!--/span-->
</div><!--/row-->
</body>
</html>