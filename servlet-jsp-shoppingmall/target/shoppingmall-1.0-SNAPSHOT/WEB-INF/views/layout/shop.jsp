<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="javax.servlet.http.HttpSession" %>

<%
    HttpSession session = request.getSession();
    boolean isLoggedIn = session.getAttribute("user_id") != null; // 세션에 사용자 정보가 있는지 확인
    String userId = (String) session.getAttribute("user_id"); // 사용자 ID 가져오기
%>

<!doctype html>
<html lang="ko">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <title>nhn아카데미 shopping mall</title>

</head>
<body>

<div class="mainContainer">
    <header class="p-3 bg-dark text-white">
        <div class="container">
            <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">

                <a href="/" class="d-flex align-items-center mb-2 mb-lg-0 text-white text-decoration-none">
                    <svg class="bi me-2" width="40" height="32" role="img" aria-label="Bootstrap"><use xlink:href="#bootstrap"></use></svg>
                </a>

                <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                    <li><a href="/index.do" class="nav-link px-2 text-secondary">Home</a></li>
                    <% if(request.getSession().getAttribute("role") == "ROLE_ADMIN") { %>
                    <li><a href="/adminPage.do" class="nav-link px-2 text-white">관리페이지</a></li>
                    <% } else { %>
                    <li><a href="#" class="nav-link px-2 text-white">마이페이지</a></li>
                    <% } %>
                </ul>

                <form class="col-12 col-lg-auto mb-3 mb-lg-0 me-lg-3">
                    <input type="search" class="form-control form-control-dark" placeholder="Search..." aria-label="Search">
                </form>

                <div class="text-end">
                    <%-- 사용자가 로그인되어 있는지 확인하여 버튼 변경 --%>
                    <% if(isLoggedIn) { %>
                    <span class="text-white me-2"><%= userId %>님</span> <!-- 사용자 ID + "님" 표시 -->
                    <a class="btn btn-outline-light me-2" href="/logout.do" >로그아웃</a>
                    <% } else { %>
                    <a class="btn btn-outline-light me-2" href="/login.do" >로그인</a>
                    <a class="btn btn-warning" href="signup.do" >회원가입</a>
                    <% } %>
                </div>

            </div>
        </div>
    </header>

    <style>
        .nav-item {
            flex: 1; /* 각 항목이 동일한 넓이를 갖도록 설정 */
            text-align: center; /* 텍스트를 가운데 정렬 */
        }

        .nav-link {
            padding: 0.5rem 1rem; /* 각 항목의 안쪽 여백 설정 */
        }
    </style>

    <!-- Category Bar -->
    <div class="category-container bg-secondary">
        <ul class="nav d-flex justify-content-around"> <!-- 각 항목을 가로 방향으로 일정한 간격으로 배치 -->
            <li class="nav-item"><a href="/index.do?categoryId=all" class="nav-link px-2 text-white">전체</a> </li><!-- 전체 항목 -->
            <c:forEach var="category" items="${categoryList}">
                <li class="nav-item">
                    <a href="/index.do?categoryId=${category.categoryId}" class="nav-link px-2 text-white">${category.categoryName}</a>
                </li>
            </c:forEach>

        </ul>
    </div>
    <!-- End Category Bar -->

    <main>
        <div class="album py-5 bg-light">
            <div class="container">
                <jsp:include page="${layout_content_holder}" />
            </div>
        </div>

    </main>

    <footer class="text-muted py-5">
        <div class="container">
            <p class="float-end mb-1">
                <a href="#">Back to top</a>
            </p>
            <p class="mb-1">shoppingmall example is © nhnacademy.com</p>
        </div>
    </footer>

</div>

</body>
</html>
