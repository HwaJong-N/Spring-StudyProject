
const signUpUser = document.querySelector("#sign-up-user");

// 버튼 클릭 이벤트 감지
signUpUser.addEventListener("click", () => {

    // 태그의 id 를 이용해 입력된 값들을 불러와 객체 생성
    const user = {
        id: document.querySelector("#id").value,
        password: document.querySelector("#password").value,
        name: document.querySelector("#name").value,
        email: document.querySelector("#email").value
    }

    // RestAPI 호출
    fetch("/signUp", {
        method: "post",
        headers: {"Content-Type": "application/json"},  // body 에 담긴 데이터 타입을 명시
        body: JSON.stringify(user)  // 생성한 객체를 JSON 형식으로 변경
    }).then(response => {
        response.text().then(textData => {
            ["error-id", "error-password", "error-name", "error-email"].forEach(tagId => {
                document.getElementById(tagId).innerText = '' })    // 기존에 출력된 오류 메세지 제거
            const res = JSON.parse(textData);   // ResponseDTO 를 JSON 형식으로 변환
            if (res.status === "OK") {
                alert("회원가입이 완료되었습니다");
                location.href = "/loginHome"
            } else if (res.status === "CONFLICT") {
                alert(res.data);    // 동일한 id 가 존재합니다
            }  else if (res.status === "BAD_REQUEST") {
                Object.entries(res.data).forEach(   // res.data 에는 오류 필드와 메세지가 담겨있음
                    ([key, value]) =>
                        document.getElementById(`${key}`).innerText = `${value}`    // 에러 메세지 출력
                );
            } else {
                alert("회원가입에 실패하였습니다");
            }
        });

    })
});