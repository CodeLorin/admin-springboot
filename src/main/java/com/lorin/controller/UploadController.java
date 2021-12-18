package com.lorin.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lorin.aop.LogAnnotation;
import com.lorin.common.ErrorCode;
import com.lorin.common.Result;
import com.lorin.service.UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

@Api(value = "文件上传Controller", tags = {"文件上传路由接口"})
@RestController
public class UploadController {
    @Autowired
    private UploadService uploadService;

    @ApiOperation(value = "文件上传接口")
    @LogAnnotation(module = "文件上传上传", operation = "文件上传上传接口")
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
