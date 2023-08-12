
const reviewCreate = document.querySelector("#reviewCreate");

// 버튼 클릭 이벤트 감지
reviewCreate.addEventListener("click", () => {
    
    // 태그의 id 를 이용해 입력된 값들을 불러와 객체 생성
    const review = {
        itemId: document.querySelector("#itemId").value, 
        star: document.querySelector("#star").value,
        nickname: document.querySelector("#nickname").value,
        comment: document.querySelector("#comment").value
    }
    
    // RestAPI 호출
    fetch("/review/add", {
        method: "post",
        headers: {"Content-Type": "application/json"},  // body 에 담긴 데이터 타입을 명시
        body: JSON.stringify(review)  // 생성한 객체를 JSON 형식으로 변경
    }).then(response => {
        const msg = (response.ok) ? "리뷰가 등록되었습니다" : "리뷰 등록에 실패하였습니다";
        alert(msg);

        // 현재 페이지 새로고침
        window.location.reload();
    })
    
});