package com.lorin.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * TODO
 * 文件上传服务类
 * @author lorin
 * @date 2021/12/15 20:42
 */


@Service
public class UploadService {
    @Value("${file.uploadPath}")
    private String uploadPath;
    @Value("${file.staticPatternPath}")
    private String staticPath;

    public Map<String, Object> uploadImg(MultipartFile multipartFile) {
        System.out.println(uploadPath);
        System.out.println(staticPath);
        try {
            // 截取文件名的后缀
            String originalFilename = multipartFile.getOriginalFilename();
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            // 生成唯一的文件名
            String fileName = UUID.randomUUID().toString().replace("-", "") + suffix;


            //拼接
            File targetPath = new File(uploadPath);
            if (!targetPath.exists()) {
                targetPath.mkdirs();
            }
            File file = new File(targetPath, fileName);
            multipartFile.transferTo(file);
            String filePath = staticPath + "/" + fileName;
            Map<String, Object> map = new HashMap<>();
            map.put("url", filePath);
            map.put("size", multipartFile.getSize());
            map.put("ext", suffix);
            map.put("filename", fileName);
            return map;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

