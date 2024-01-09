// 양식에서 추가하는 데이터?
/*
파일 업로드 후 후속 처리 함수
@params files : 업로드 한 파일 정보 목록
*/
function callbackFileUpload(files) { // 파일 올리고 나서 해야하는 작업은 때에 따라 다르다
    console.log(files);
    if (!files || files.length == 0) {
        return;
    }

    const file = files[0];

    let html = document.getElementById("image1_tpl").innerHTML;
    console.log(html);

    const imageUrl = file.thumbsUrl.length > 0 ? file.thumbsUrl.pop() : file.fileUrl;
    // 썸네일 마지막 이미지를 가져오고 없으면 ?
    const seq = file.seq;

    // 템플릿 치환 코드
    html = html.replace(/\[seq\]/g, seq)
                .replace(/\[imageUrl\]/g, imageUrl);

    const domParser = new DOMParser();
    const dom = domParser.parseFromString(html, "text/html");

    const imageTplEl = dom.querySelector(".image1_tpl_box");

    const profileImage = document.getElementById("profile_image");
    profileImage.innerHTML = "";

    console.log(dom);

    profileImage.appendChild(imageTplEl); // 기존거 지우고 다시 추가?

}

/**
파일 삭제 후 후속처리 함수
@param seq : 파일 등록 번호
*/
function callbackFileDelete(seq) {
    const fileEl = document.getElementById(`file_${seq}`);
    fileEl.parentElement.removeChild(fileEl);
}