package org.choongang.file.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.choongang.file.entities.FileInfo;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
@RequiredArgsConstructor
public class FileDownloadService {

    private final FileInfoService infoService;
    private final HttpServletResponse response;

    public void download(Long seq) { // 파일번호로 다운로드
        FileInfo data = infoService.get(seq);
        // String fileName = data.getFileName();
        String filePath = data.getFilePath();

        // 파일명 -> 2바이트 인코딩으로 변경 (윈도우즈 시스템에서 한글 깨짐 방지)
        String fileName = null;
        try {
            fileName = new String(data.getFileName().getBytes(), "ISO8859_1");
            // 데이터에있는 fileName.getBytes()를 ISO8859_1(2byte)로 바꾼다.
        } catch (UnsupportedEncodingException e) {

        }

        File file = new File(filePath);
        try (FileInputStream fis = new FileInputStream(file);
             BufferedInputStream bis = new BufferedInputStream(fis)) {
            OutputStream out = response.getOutputStream(); // 응답 Body에 출력

            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            response.setHeader("Content-Type", "application/octet-stream");
            response.setIntHeader("Expires", 0); // 만료시간x, 용량큰 파일도 다운받기
            response.setHeader("Cache-Control", "must-revalidate"); // 밑에랑 동일
            // Cache 갱신
            response.setHeader("Pragma", "public"); // 옛날 브라우저용
            response.setHeader("Content-Length", String.valueOf(file.length()));

            while (bis.available() > 0) {
                out.write(bis.read());
            }
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
