package com.lorin.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lorin.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * TODO
 *
 * @author lorin
 * @date 2021/12/15 20:48
 */


@Controller
public class UploadController {
    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile multipartFile) throws JsonProcessingException {
        if (multipartFile.isEmpty()) {
            return "文件为空";
        }
        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(uploadService.uploadImg(multipartFile));
    }
}
