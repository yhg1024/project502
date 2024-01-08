var commonLib = commonLib || {};
/*
1. 파일 업로드
*/
commonLib.fileManager = {
    /*
    파일 업로드 처리
    */
    update(files) {
        try {
            if (!files || files.length == 0) {
                throw new Error("업로드할 파일을 선택하세요.");
            }

            // gid
            const gidEl = document.querySelector("[name='gid']");
            if (!gidEl || !gidEl.value.trim()) {
                throw new Error("gid가 누락되었습니다.");
            }

            const gid = gidEl.value.trim();

            const formData = new FormData(); // 기본 Content-Type: multipart/form-data
            formData.append("gid", gid);

            for (const file of files) {
                formData.append("file", file);
            }

        } catch (err) {
            alert(err.message);
            console.error(err);
        }
    }
};

// 이벤트 처리
window.addEventListener("DOMContentLoaded", function() {
    const uploadFiles = document.getElementsByClassName("upload_files");

    const fileEl = document.createElement("input");
    fileEl.type = "file";
    fileEl.multiple = true; // 여러개 파일 선택

    // 파일 업로드 버튼 클릭 처리 -> 파일 탁색기 열기
    for (const el of uploadFiles) {
        el.addEventListener("click", function() {
            fileEl.click();
        });
    }

    // 파일 선택시 이벤트 처리
    fileEl.addEventListener("change", function(e) {
        commonLib.fileManager.upload(e.target.files);
    });
});