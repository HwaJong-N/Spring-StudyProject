// 삭제 버튼 선택
const reviewDelete = document.querySelectorAll(".reviewDelete");

reviewDelete.forEach(btn => {
    // 각 버튼의 이벤트 처리 등록
    btn.addEventListener("click", event => {
        // 이벤트 발생 요소 선택
        const deleteBtn = event.target;

        // 삭제할 리뷰 id 가져오기
        const reviewId = deleteBtn.getAttribute("review-id");

        // 삭제 API 호출 및 처리
        fetch(`/review/delete/${reviewId}`, {
            method: "DELETE"
        }).then(response => {
            const msg = (response.ok) ? "리뷰가 삭제되었습니다.": "리뷰 삭제에 실패하였습니다";
            alert(msg);

            window.location.reload();
        });
    })
})
