<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" session="false" %>

<div style="margin: auto; width: 400px;">
    <div class="p-2">
        <form method="post" action="/categoryAddAction.do">

            <h1 class="h3 mb-3 fw-normal">카테고리 정보 입력</h1>

            <div class="form-floating">
                <input type="text" name="category_id" class="form-control" id="category_id" placeholder="회원 아이디" required>
                <label for="category_id">카테고리 아이디</label>
            </div>

            <div class="form-floating">
                <input type="text" name="category_name" class="form-control" id="category_name" placeholder="이름" required>
                <label for="category_name">카테고리 이름</label>
            </div>

            <button class="w-100 btn btn-lg btn-primary mt-3" type="submit">카테고리 등록</button>

            <%-- 서버에서 전달된 결과 메시지를 출력하는 부분 --%>
            <h1 style="color: red;" class="h3 mb-3 fw-normal">${categoryAddMessage}</h1>

            <p class="mt-5 mb-3 text-muted">© 2022-2024</p>


        </form>
    </div>
</div>
