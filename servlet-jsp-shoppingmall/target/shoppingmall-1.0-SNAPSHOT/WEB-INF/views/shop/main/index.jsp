<%@ page import="com.nhnacademy.shoppingmall.product.domain.Product" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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
            <img src= "<%= product.getProductImagePath() %>" alt="No Image" width="100%" height="225">
            <div class="card-body">
                <p class="card-text"><%= product.getProductName() %></p>
                <p class="card-text"><%= product.getProductPrice() %> 원</p>
                <div class="d-flex justify-content-between align-items-center">
                    <div class="btn-group">
                        <a href="/productView.do?productId=<%= product.getProductId() %>" class="btn btn-sm btn-outline-secondary">보기</a>
                        <%-- 관리자일 경우에만 Edit 버튼 표시 --%>
                        <% Object roleObj = request.getSession().getAttribute("role");
                            if (roleObj != null && roleObj.equals("ROLE_ADMIN")) { %>
                        <a href="productEdit.do?productId=<%= product.getProductId() %>" class="btn btn-sm btn-outline-secondary">편집</a>
                        <a href="productDelete.do?productId=<%= product.getProductId() %>" class="btn btn-sm btn-outline-secondary">삭제</a>
                        <% } %>
                    </div>
                    <small class="text-muted"><%= product.getProductRdate() %></small>
                </div>
            </div>
        </div>
    </div>
    <% } %>

</div>
