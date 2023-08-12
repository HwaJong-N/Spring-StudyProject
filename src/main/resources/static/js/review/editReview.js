// 1. Modal 에 기존 데이터 전달
const reviewEditModal = document.querySelector("#review-edit-modal");

reviewEditModal.addEventListener("show.bs.modal", event => {
    // Modal 트리거 버튼 선택
    const triggerBtn = event.relatedTarget;

    // Modal 트리거 버튼 데이터 가져오기
    const id = triggerBtn.getAttribute("data-bs-id");
    const itemId = triggerBtn.getAttribute("data-bs-item-id");
    const nickname = triggerBtn.getAttribute("data-bs-nickname");
    const star = triggerBtn.getAttribute("data-bs-star");
    const comment = triggerBtn.getAttribute("data-bs-comment");

    // Modal 에 데이터 전달
    document.querySelector("#modal-id").value = id;
    document.querySelector("#modal-item-id").value = itemId;
    document.querySelector("#modal-nickname").value = nickname;
    document.querySelector("#modal-star").value = star;
    document.querySelector("#modal-comment").value = comment;
})



// 2. 수정된 데이터 처리
const modalUpdate = document.querySelector("#modal-update");

// 클릭 이벤트 감지 및 처리 ( RestAPI 호출 )
modalUpdate.addEventListener("click", () => {

    // 수정 댓글 객체 생성
    const comment = {
        id: document.querySelector("#modal-id").value,
        itemId: document.querySelector("#modal-item-id").value,
        nickname: document.querySelector("#modal-nickname").value,
        star: document.querySelector("#modal-star").value,
        comment: document.querySelector("#modal-comment").value
    };

    // 수정 Rest API 호출
    fetch("/review/edit", {
        method: "PATCH",
        headers: { "Content-Type": "application/json"},
        body: JSON.stringify(comment)
    }).then(response => {
        const msg = (response.ok) ? "리뷰가 수정되었습니다.": "리뷰 수정에 실패하였습니다";
        alert(msg);
        
        window.location.reload();
    });
})
