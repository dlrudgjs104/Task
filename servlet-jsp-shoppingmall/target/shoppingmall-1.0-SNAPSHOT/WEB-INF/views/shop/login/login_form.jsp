<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" session="false" %>

<div style="margin: auto; width: 400px;">
    <div class="p-2">
        <form method="post" action="/loginAction.do">

            <h1 class="h3 mb-3 fw-normal">로그인 정보 입력</h1>

            <div class="form-floating">
                <input type="text" name="user_id" class="form-control" id="user_id" placeholder="회원 아이디" required>
                <label for="user_id">회원아이디</label>
            </div>

            <div class="form-floating">
                <input type="password" name="user_password" class="form-control" id="user_password" placeholder="비밀번호" required>
                <label for="user_password">비밀번호</label>
            </div>

            <button class="w-100 btn btn-lg btn-primary mt-3" type="submit">로그인</button>

            <%-- 서버에서 전달된 결과 메시지를 출력하는 부분 --%>
            <h1 style="color: red;" class="h3 mb-3 fw-normal">${loginMessage}</h1>

            <p class="mt-5 mb-3 text-muted">© 2022-2024</p>

        </form>
    </div>
</div>