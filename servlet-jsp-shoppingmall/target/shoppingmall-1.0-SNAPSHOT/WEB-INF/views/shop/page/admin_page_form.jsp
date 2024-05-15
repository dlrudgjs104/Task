<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row">
    <div class="col-12">
        <div class="mb-3">
            <a href="/userList.do" class="btn btn-primary me-2">계정 리스트</a>
            <a href="/categoryList.do" class="btn btn-primary me-2">카테고리 리스트</a>
            <a href="/productList.do" class="btn btn-primary">상품 리스트</a>
        </div>
    </div>
</div>

<c:if test="${listKind == 'category'}">
    <div class="row">
        <div class="col-12">
            <table class="table">
                <thead>
                <tr>
                    <th>카테고리 아이디</th>
                    <th>카테고리 이름</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="category" items="${categoryList}">
                    <tr>
                        <td>${category.categoryId}</td>
                        <td>${category.categoryName}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</c:if>

<c:if test="${listKind == 'product'}">
    <div class="row">
        <div class="col-12">
            <table class="table">
                <thead>
                <tr>
                    <th>상품 아이디</th>
                    <th>상품 카테고리</th>
                    <th>상품 이름</th>
                    <th>상품 가격</th>
                    <th>상품 설명</th>
                    <th>상품 등록일자</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="product" items="${productList}">
                    <tr>
                        <td>${product.productId}</td>
                        <td></td>
                        <td>${product.productName}</td>
                        <td>${product.productPrice}</td>
                        <td>${product.productDescription}</td>
                        <td>${product.productRdate}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</c:if>

<c:if test="${listKind == 'user'}">
    <div class="row">
        <div class="col-12">
            <table class="table">
                <thead>
                <tr>
                    <th>유저 아이디</th>
                    <th>유저 이름</th>
                    <th>유저 패스워드</th>
                    <th>유저 생년월일</th>
                    <th>유저 권한</th>
                    <th>유저 포인트</th>
                    <th>유저 생성날짜</th>
                    <th>유저 마지막 로그인 시간</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="user" items="${userList}">
                    <tr>
                        <td>${user.userId}</td>
                        <td>${user.userName}</td>
                        <td>${user.userPassword}</td>
                        <td>${user.userBirth}</td>
                        <td>${user.userAuth}</td>
                        <td>${user.userPoint}</td>
                        <td>${user.createdAt}</td>
                        <td>${user.latestLoginAt}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</c:if>
