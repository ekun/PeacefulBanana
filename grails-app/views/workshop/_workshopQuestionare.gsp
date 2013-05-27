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