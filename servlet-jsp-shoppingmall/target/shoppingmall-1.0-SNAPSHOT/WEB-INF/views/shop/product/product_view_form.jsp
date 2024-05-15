<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container">
    <div class="row">
        <div class="col">
            <h1>제품 상세 정보</h1>
        </div>
    </div>
    <div class="row">
        <div class="col-md-3">
            <img src="${product.productImagePath}" alt="Product Image" class="img-fluid" style="max-width: 300px; height: 300px;">
        </div>
        <div class="col-md-9">
            <table class="table">
                <tr>
                    <th style="width: 150px;">제품 카테고리</th>
                    <td>${categoryNames}</td>
                </tr>
                <tr>
                    <th>제품 아이디</th>
                    <td>${product.productId}</td>
                </tr>
                <tr>
                    <th>제품 이름</th>
                    <td>${product.productName}</td>
                </tr>
                <tr>
                    <th>제품 가격</th>
                    <td>${product.productPrice} 원</td>
                </tr>
                <tr>
                    <th>제품 설명</th>
                    <td>${product.productDescription}</td>
                </tr>
                <tr>
                    <th>제품 등록일자</th>
                    <td>${product.productRdate}</td>
                </tr>
            </table>
        </div>
    </div>
</div>
