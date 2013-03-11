<%@ page import="org.joda.time.DateTime" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
    <style type="text/css">
        body {
            font-family: Verdana;
        }
    </style>
</head>
<body>
    <h1>Workshop Questionare</h1>
    <p>From <joda:time value="${new DateTime(workshop.dateStart)}">
        <joda:format value="${it}" pattern="yyyy-MM-dd" />
    </joda:time> to <joda:time value="${new DateTime(workshop.dateEnd)}">
        <joda:format value="${it}" pattern="yyyy-MM-dd" />
    </joda:time>.</p>
    <hr />
    <h2>Questions</h2>
    <g:each in="${questions}">
        <p>${it.questionText}</p>
        <p style="height: 120px; width: 100%; border: 2px solid #000000;"></p>
    </g:each>
</body>
</html>