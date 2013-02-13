<html>
<head>
    <meta name="layout" content="main"/>
    <title>Reflection workshop</title>
    <r:require modules="bootstrap"/>
    <r:require modules="bootstrap-transition"/>
</head>
<body>
<div class="row-fluid">
    <div class="span3">
        <div class="well sidebar-nav">
            <ul class="nav nav-list">
                <li class="nav-header">Reflection Workshop</li>
                <li class="active"><a href="${createLink(action: '')}">Workshops</a></li>
            </ul>
        </div><!--/.well -->
    </div><!--/span-->
    <div class="span9">
        <h1>Workshop</h1>
        <div id="target">
            <g:formatWorkshopQuestions questions="${questions}" tags="${tags}" />
        </div>
    </div><!--/span-->
</div><!--/row-->
<g:javascript>
    function reloadList() {
            $.ajax({type: "POST",
                url: "${createLink(controller: 'workshop', action: 'ajaxGetQuestions', params: params)}",
                success: function(msg){
                    document.getElementById('target').innerHTML = msg;
                    $("#collapseOne").collapse('hide');
                    $("#collapseTwo").collapse('show');
                }
            });

        }

    function reloadList2() {
            $.ajax({type: "POST",
                url: "${createLink(controller: 'workshop', action: 'ajaxGetQuestions', params: params)}",
                success: function(msg){
                    document.getElementById('target').innerHTML = msg;
                    $("#collapseOne").collapse('hide');
                    $("#collapseThree").collapse('show');
                }
            });

        }
</g:javascript>
</body>
</html>