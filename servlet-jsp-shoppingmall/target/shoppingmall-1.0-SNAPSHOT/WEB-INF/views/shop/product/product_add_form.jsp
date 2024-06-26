<%@ page import="com.nhnacademy.shoppingmall.Category.domain.Category" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div style="margin: auto; width: 800px;">
    <div class="p-2">
        <h1 class="h3 mb-3 fw-normal">제품 정보 입력</h1>

        <form method="post" action="/productAddAction.do" enctype="multipart/form-data">

            <div class="form-control">
                <input type="file" name="product_image" id="product_image">
            </div>

            <div class="row">
                <div class="col">
                    <div class="form-floating">
                        <select name="category_id1" class="form-select" id="category_id1" required>
                            <c:forEach var="category" items="${categoryList}">
                                <option value="${category.categoryId}">${category.categoryName}</option>
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
                                <option value="${category.categoryId}">${category.categoryName}</option>
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
                                <option value="${category.categoryId}">${category.categoryName}</option>
                            </c:forEach>
                        </select>
                        <label for="category_id3">제품 카테고리3(선택)</label>
                    </div>
                </div>
            </div>




            <div class="form-floating">
                <input type="text" name="product_id" class="form-control" id="product_id" placeholder="제품 아이디" required>
                <label for="product_id">제품 아이디</label>
            </div>

            <div class="form-floating">
                <input type="text" name="product_name" class="form-control" id="product_name" placeholder="제품 이름" required>
                <label for="product_name">제품 이름</label>
            </div>

            <div class="form-floating">
                <input type="number" name="product_price" class="form-control" id="product_price" placeholder="제품 가격" required>
                <label for="product_price">제품 가격</label>
            </div>

            <div class="form-floating">
                <textarea style="height: 200px;" name="product_description" class="form-control" id="product_description" placeholder="제품 설명" required></textarea>
                <label for="product_description">제품 설명</label>
            </div>

            <button class="w-100 btn btn-lg btn-primary mt-3" type="submit">제품 등록</button>

            <%-- 서버에서 전달된 결과 메시지를 출력하는 부분 --%>
            <h1 style="color: red;" class="h3 mb-3 fw-normal">${productAddMessage}</h1>

        </form>
    </div>
</div>
