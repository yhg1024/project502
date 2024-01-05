package org.choongang.file.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.commons.ExceptionRestProcessor;
import org.choongang.commons.rests.JSONData;
import org.choongang.file.entities.FileInfo;
import org.choongang.file.service.FileUploadService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class ApiFileController implements ExceptionRestProcessor { // 통일된 방식으로 에러 응답

    private final FileUploadService uploadService;

    @PostMapping
    public JSONData<List<FileInfo>> upload(@RequestParam("file") MultipartFile[] files,
                           @RequestParam(name = "gid", required = false) String gid,
                           // gid는 필수, 없으면 기본값을 넣는다. location은 필수는 아니다
                           @RequestParam(name = "location", required = false) String location) {

        List<FileInfo> uploadedFiles = uploadService.upload(files, gid, location);


        return new JSONData<>(uploadedFiles);
    }
}
