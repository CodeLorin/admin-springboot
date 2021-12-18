package com.lorin.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lorin.common.ErrorCode;
import com.lorin.common.Result;
import com.lorin.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * TODO
 *
 * @author lorin
 * @date 2021/12/15 20:48
 */


@RestController
public class UploadController {
    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    @ResponseBody
    public Result upload(@RequestParam("file") MultipartFile multipartFile) throws JsonProcessingException {
        if (multipartFile.isEmpty()) {
            return Result.fail(ErrorCode.FILE_NOT_SELECT.getMsg());
        }
        ObjectMapper mapper = new ObjectMapper();
        return Result.success(mapper.writeValueAsString(uploadService.uploadImg(multipartFile)), "文件上传成功");
    }
}
