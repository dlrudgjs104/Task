<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" session="false" %>

<div style="margin: auto; width: 800px;">
    <div class="p-2">
        <h1 class="h3 mb-3 fw-normal">썸네일 이미지 업로드</h1>
        <div class="form-control">
            <form method="post" action="/productFileUpload.do" enctype="multipart/form-data">
                <input type="file" name="product_image" id="product_image" required>
                <input type="submit" />
            </form>
        </div>

        <form method="post" action="/productAddAction.do">

            <h1 class="h3 mb-3 fw-normal" style="margin-top: 20px;">제품 정보 입력</h1>

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

            <p class="mt-5 mb-3 text-muted">© 2022-2024</p>
        </form>
    </div>
</div>
