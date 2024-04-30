<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page errorPage="/error.jsp" %>

<html>
<head>
    <title>student - list</title>
    <link rel="stylesheet" href="/style.css" />
</head>

<body>
<h1>학생 리스트</h1>
<p><a href="/student/register.do" >학생(등록)</a></p>
<table border="1">
    <thead>
    <tr>
        <th style="width: 35%">아이디</th>
        <th style="width: 35%">이름</th>
        <th style="width: 10%">성별</th>
        <th style="width: 10%">나이</th>
        <th style="width: 10%">cmd</th>
    </tr>
    </thead>
    <tbody>
    <!--todo list 구현하기 c:foreach -->
    <c:forEach var="student" items="${studentList}">
        <tr>
            <td>${student.id}</td>
            <td>${student.name}</td>
            <td>${student.gender}</td>
            <td>${student.age}</td>
            <td>
                <c:url var="view_link" value="/student/view.do" scope="request">
                <c:param name="id" value="${student.id}" />
            </c:url>
                <a href="${view_link}">조회</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>