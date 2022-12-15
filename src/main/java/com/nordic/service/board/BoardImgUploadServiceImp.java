package com.nordic.service.board;

import com.nordic.repository.board.BoardImgDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardImgUploadServiceImp implements BoardImgUploadService{

    private final BoardImgDao dao;

    @Override
    public List upload(List<MultipartFile> files) throws IOException {
        String PATH = System.getProperty("user.dir") + "/src/main/resources/static/img/board/";
        log.info("path - {}", PATH);
        log.info("multiFiles - {}", files);
        List<String> image_file = new ArrayList<>();
        File isDir = new File(PATH);

        if(!isDir.exists()) isDir.mkdirs();
        for(MultipartFile file : files) {
            String originalName = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();
            String extension = originalName.substring(originalName.lastIndexOf("."));
            String randomName = PATH + uuid + extension;
            file.transferTo(new File(randomName));
            log.info("randomName - {}", randomName);

            image_file.add(randomName);
        }

        return image_file;
    }

    @Override
    public int input(Map<String, Object> param) throws Exception {
        int result = 0;
        List<String> image_file = (ArrayList) param.get("image_file");
        List<String> orignal_name = (ArrayList) param.get("orignal_name");

        if(image_file.size() > 1) {
            for (int i = 0; i < image_file.size(); i++) {
                param.replace("image_file", image_file.get(i));
                param.replace("orignal_name", orignal_name.get(i));
                result += dao.inputImage(param);
            }
        } else if(image_file.size() == 1) {
            param.replace("image_file", image_file.get(0));
            param.replace("orignal_name", orignal_name.get(0));
            result += dao.inputImage(param);
        }

        return result;
    }

    @Override
    public int delete(Map<String, Object> param) throws Exception {
        return dao.deleteImage(param);
    }
}
