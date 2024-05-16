<%@ page import="com.nhnacademy.shoppingmall.Category.domain.Category" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form method="post" action="/productEditAction.do" enctype="multipart/form-data">
    <div class="row">
        <div class="col">
            <h1>제품 정보 수정</h1>
        </div>
    </div>

    <div class="row">
        <div class="col-md-3">
            <img src="${product.productImagePath}" alt="Product Image" class="img-fluid" style="max-width: 300px; height: 300px;">
            <input type="text" name="product_image_path" class="form-control" id="product_image_path" value="${product.productImagePath}" style="display: none;">
            <input type="file" name="product_image" id="product_image">

<%--            <button class="w-100 btn btn-lg btn-primary mt-3" type="submit">썸네일 등록</button>--%>
        </div>

        <div class="col-md-9">
            <table class="table">
                <tr>
                    <th style="width: 150px;">제품 아이디</th>
                    <td><input type="text" name="product_id" class="form-control" id="product_id" value="${product.productId}" readonly></td>
                </tr>
                <tr>
                    <th>제품 카테고리</th>
                    <td>
                        <div class="row">
                            <div class="col">
                                <div class="form-floating">
                                    <select name="category_id1" class="form-select" id="category_id1" required>
                                        <c:forEach var="category" items="${categoryList}">
                                            <option value="${category.categoryId}" <c:if test="${category.categoryId == category_id1}">selected</c:if>>${category.categoryName}</option>
                                        </c:forEach>
                                    </select>
                                    <label for="category_id1">제품 카테고리1(필수)</label>
                                </div>
                            </div>

                            <div class="col">
                                <div class="form-floating">
                                    <select name="category_id2" class="form-select" id="category_id2" required>
                                        <option value=null>없음</option>
                                        <c:forEach var="category" items="${categoryList}">
                                            <option value="${category.categoryId}" <c:if test="${category.categoryId == category_id2}">selected</c:if>>${category.categoryName}</option>
                                        </c:forEach>
                                    </select>
                                    <label for="category_id2">제품 카테고리2(선택)</label>
                                </div>
                            </div>

                            <div class="col">
                                <div class="form-floating">
                                    <select name="category_id3" class="form-select" id="category_id3" required>
                                        <option value=null>없음</option>
                                        <c:forEach var="category" items="${categoryList}">
                                            <option value="${category.categoryId}" <c:if test="${category.categoryId == category_id3}">selected</c:if>>${category.categoryName}</option>
                                        </c:forEach>
                                    </select>
                                    <label for="category_id3">제품 카테고리3(선택)</label>
                                </div>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <th>제품 이름</th>
                    <td><input type="text" name="product_name" class="form-control" id="product_name" value="${product.productName}" required></td>
                </tr>
                <tr>
                    <th>제품 가격</th>
                    <td><input type="number" name="product_price" class="form-control" id="product_price" value="${product.productPrice}" required></td>
                </tr>
                <tr>
                    <th>제품 설명</th>
                    <td><textarea style="height: 200px;" name="product_description" class="form-control" id="product_description" required>${product.productDescription}</textarea></td>
                </tr>
                <tr>
                    <th>제품 등록일자</th>
                    <td id="productRdate">${product.productRdate}</td>
                </tr>
            </table>
            <button class="w-100 btn btn-lg btn-primary mt-3" type="submit">제품 수정</button>

            <%-- 서버에서 전달된 결과 메시지를 출력하는 부분 --%>
            <h1 style="color: red;" class="h3 mb-3 fw-normal">${productEditMessage}</h1>
        </div>
    </div>
</form>

