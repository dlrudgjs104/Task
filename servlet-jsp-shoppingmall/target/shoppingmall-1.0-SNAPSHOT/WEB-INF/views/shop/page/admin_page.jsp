<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div style="margin: auto; width: 400px;">
    <div class="p-2">
        <form action="/uploadProduct.do" method="post" enctype="multipart/form-data">
            <div class="mb-3">
                <label for="imageUpload" class="form-label">상품 이미지 업로드</label>
                <input type="file" class="form-control" id="imageUpload" name="imageUpload">
            </div>
            <button type="submit" class="btn btn-primary">이미지 업로드</button>
        </form>
    </div>

    <div class="p-2">
        <img src="<c:url value='/resources/no-image.png'/>" alt="No Image" class="img-fluid">
    </div>
</div>
