package com.example.myblog.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class PhotoUtil {

    @Value("${postImgLocation}")
    private String postImgLocation;


    public String ckUpload(MultipartHttpServletRequest request) {
        //에디터의 name="upload"
        MultipartFile uploadFile = request.getFile("upload");

        //8840ebc8-df01-4fe5-95ec-3f9959a203e3.jpg
        String fileName = getFileName(uploadFile); //저장할 파일이름

        //C:/blog/post/
        String realPath = getPath(request); //파일을 저장할 경로

        // C:/blog/post/8840ebc8-df01-4fe5-95ec-3f9959a203e3.jpg
        String savePath = realPath + fileName; //파일을 저장할 실제 서버 경로 + 파일명

        // localhost/images/8840ebc8-df01-4fe5-95ec-3f9959a203e3.jpg
        String uploadPath = "/images/" + fileName; //웹에서 보는 경로

        uploadFile(savePath, uploadFile); //서버에 실제 파일 업로드

        System.out.println("uploadPath: " + uploadPath);

        return uploadPath;
    }

    //파일 업로드 메소드
    private void uploadFile(String savePath, MultipartFile uploadFile) {
        File file = new File(savePath); //savePath: 파일을 저장할 경로

        try {
            uploadFile.transferTo(file); //파일이 서버에 저장되는 순간
        } catch (IOException e) {
            throw new RuntimeException("파일 업로드를 실패했습니다." ,e);
        }
    }

    //파일 이름 얻는 메소드
    private String getFileName(MultipartFile uploadFile) {
        //파일의 원래 이름: test.jpg
        String originalFileName = uploadFile.getOriginalFilename();

        //이미지의 확장자명을 구한다
        String ext = originalFileName.substring(originalFileName.lastIndexOf("."));

        //8840ebc8-df01-4fe5-95ec-3f9959a203e3.jpg
        return UUID.randomUUID() + ext; //중복되지 않는 이미지명을 return해준다
    }

    //경로 얻는 메소드
    private String getPath(MultipartHttpServletRequest request) {
        //실제 서버내 파일 저장 경로
        //postImgLocation=C:/blog/post
        String realPath = postImgLocation + "/";
        System.out.println("realPath: " + realPath);

        Path directoryPath = Paths.get(realPath);

        if(!Files.exists(directoryPath)) { //해당 디렉터리가 존재하지 않는다면
            try {
                Files.createDirectories(directoryPath); //디렉터리 생성
            } catch (Exception e) {
                throw new RuntimeException("업로드할 디렉터리가 존재하지 않습니다.", e);
            }
        }

        return realPath;
    }

}
