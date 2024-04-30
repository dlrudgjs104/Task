<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page errorPage="/error.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %>
</h1>
<br/>
<ol>
    <li><P>JSP 목록</P></li>
    <li><a href="hello-servlet">Hello Servlet</a></li>
    <li><a href="/student/list.jsp">Student List</a></li>
    <li><a href="/student/register.jsp">Student Register</a></li>
    <li><a href="/errortest">Error Test</a></li>

    <li><P>실행결과 목록</P></li>
    <li><a href="/student/list.do">Student List</a></li>
    <li><a href="/student/register.do">Student Register</a></li>
    <li><a href="/student/update.do">Student Update</a></li>
    <li><a href="/student/delete.do">Student Delete</a></li>

</ol>


</body>
</html>