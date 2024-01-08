var commonLib = commonLib || {}; // 하위속성값으로 namespace를 정할 수있다?

/*
ajax 요청, 응답 편의 함수
@param method: 요청 방식 - GET, POST, PATCH, DELETE ...
@param url : 요청 URL
@param params : 요청 데이터(POST, PUT, PATCH)
@param responseType : json : javascript 객체로 변환
*/
commonLib.ajaxLoad = function(method, url, params, responseType) {
    method = method || "GET";
    params = params || null;

    return new Promise((resolve, reject) => {
        const xhr = XMLHttpRequest();
        xhr.open(method, url);
        xhr.send(params); // 요청 바디에 실릴 데이터 키=값&키=값& ... FormData 객체(POST, PATCH, PUT)

        xhr.onreadystatechange = function() {
            if (xhr.status == 200 && xhr.readySate == XMLHttpRequest.DONE) {
                const resData = (responseType && responseType.toLowerCase() == 'json') ? JSON.parse(xhr.responseText) : xhr.responseText;

                resolve(resData); // 성공시 응답 데이터
            }
        };

        xhr.onaboart = function(err) { // 중간에 멈췄을 때
            reject(err); // 중단 시
        };

        xhr.onerror = function(err) {
            reject(err); // 요청 또는 응답시 오류 발생
        }
    });
};