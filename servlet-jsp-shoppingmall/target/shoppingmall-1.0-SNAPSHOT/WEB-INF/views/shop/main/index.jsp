<%@ page import="com.nhnacademy.shoppingmall.product.domain.Product" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: nhn
  Date: 2023/11/08
  Time: 10:20 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- 이 이미지가 노출될 경우 대체 이미지로 대체합니다. --%>
<% String noImage = request.getContextPath() + "/resources/no-image.png"; %>
<div class="row">
    <div class="col-12">
        <div class="text-end mb-3">
            <%-- 상품 추가 버튼 --%>
            <% if (request.getSession().getAttribute("role") == "ROLE_ADMIN") { %>
            <a href="/categoryAdd.do" class="btn btn-primary">카테고리 추가</a>
            <a href="/productAdd.do" class="btn btn-primary">상품 추가</a>
            <% } %>
        </div>
    </div>
</div>

<div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
    <% List<Product> productList = (List<Product>) request.getAttribute("productList"); %>
    <% for (Product product : productList) { %>
    <div class="col">
        <div class="card shadow-sm">
<%--            <img src="<%= product.getImageUrl() %>" alt="<%= product.getProductName() %>" width="100%" height="225">--%>
                <img src="<%= noImage %>" alt="No Image" width="100%" height="225">
            <div class="card-body">
                <p class="card-text"><%= product.getProductName() %></p>
                <p class="card-text"><%= product.getProductPrice() %></p>
                <div class="d-flex justify-content-between align-items-center">
                    <div class="btn-group">
                        <button type="button" class="btn btn-sm btn-outline-secondary">보기</button>
                        <%-- 관리자일 경우에만 Edit 버튼 표시 --%>
                        <% Object roleObj = request.getSession().getAttribute("role");
                            if (roleObj != null && roleObj.equals("ROLE_ADMIN")) { %>
                        <button type="button" class="btn btn-sm btn-outline-secondary">편집</button>
                        <button type="button" class="btn btn-sm btn-outline-secondary">삭제</button>
                        <% } %>
                    </div>
                    <small class="text-muted">9 mins</small>
                </div>
            </div>
        </div>
    </div>
    <% } %>

</div>
