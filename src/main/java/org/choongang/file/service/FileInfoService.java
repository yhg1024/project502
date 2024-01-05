package org.choongang.file.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.choongang.configs.FileProperties;
import org.choongang.file.entities.FileInfo;
import org.choongang.file.repositories.FileInfoRepository;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

// 조회 - 목록 :  gid, location, 낱개 조회 :
@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(FileProperties.class)
public class FileInfoService {

    private final FileProperties fileProperties;
    private final FileInfoRepository repository;
    private final HttpServletRequest request;

    public FileInfo get(Long seq) {
        FileInfo fileInfo = repository.findById(seq).orElseThrow(FileNotFoundException::new);

        addFileInfo(fileInfo); // 파일 추가 정보 처리

        return fileInfo;
    }

    /**
     * 파일 추가 정보 처리
     *      - 파일 서버 경로 (filePath)
     *      - 파일 URL(fileUrl)
     * @param fileInfo
     */
    public void addFileInfo(FileInfo fileInfo) {
        long seq = fileInfo.getSeq();
        long dir = seq % 10L;
        String fileName = seq + fileInfo.getExtension();

        // 서버에 올라가는 실제 파일 경로
        String filePath = fileProperties.getPath() + dir + "/" + fileName;
        // 접근할 수있는 URL
        String fileUrl = request.getContextPath() + fileProperties.getUrl() + dir + "/" + fileName;
        fileInfo.setFilePath(filePath);
        fileInfo.setFileUrl(fileUrl);
    }
}
