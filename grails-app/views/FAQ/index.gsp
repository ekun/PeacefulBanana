<html>
<head>
    <meta name="layout" content="main"/>
    <title>FAQ</title>
    <r:require modules="bootstrap"/>
    <r:require modules="bootstrap-transition"/>
</head>
<body>
<div class="row-fluid">
    <div class="span3">
        <div class="well sidebar-nav">
            <ul class="nav nav-list">
                <li class="nav-header">FAQ</li>
                <li class="active"><a href="${createLink(controller: 'FAQ',action: '')}">Workshops</a></li>
            </ul>
        </div><!--/.well -->
    </div><!--/span-->
    <div class="span9">
        <h1>FAQ</h1>
        <div class="accordion" id="accordion2" style="margin-bottom: 50px;">
            <div class="accordion-group">
                <div class="accordion-heading">
                    <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapseOne">
                        General
                    </a>
                </div>
                <div id="collapseOne" class="accordion-body collapse in">
                    <div class="accordion-inner">
                        <ul>
                            <li style="padding: 15px 15px;"></li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="accordion-group">
                <div class="accordion-heading">
                    <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapseTwo">
                        Reflection
                    </a>
                </div>
                <div id="collapseTwo" class="accordion-body collapse">
                    <div class="accordion-inner">
                        <ul>
                             <li style="padding: 15px 15px;"></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div><!--/span-->
</div><!--/row-->
</body>
</html>