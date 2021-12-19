package com.lorin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.lorin.aop.LogAnnotation;
import com.lorin.common.ErrorCode;
import com.lorin.common.Result;
import com.lorin.entity.Student;
import com.lorin.entity.Teacher;
import com.lorin.service.StudentService;
import com.lorin.service.TeacherService;
import com.lorin.service.UploadService;
import com.lorin.utils.FaceEngineUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author lorin
 * @date 2021/12/19 0:21
 */

@Api(value = "人脸识别", tags = {"人脸识别路由接口"})
@RestController
public class FaceController {

    @Autowired
    private UploadService uploadService;
    @Autowired
    StudentService studentService;
    @Autowired
    TeacherService teacherService;
    @ApiOperation(value = "人脸识别")
    @LogAnnotation(module = "人脸识别", operation = "人脸识别接口")
    @PostMapping("/face")
    @ResponseBody
    public Result upload(@RequestParam("file") MultipartFile multipartFile) throws JsonProcessingException {
        if (multipartFile.isEmpty()) {
            return Result.fail(ErrorCode.FILE_NOT_SELECT.getMsg());
        }
        Map<String, Object> map = uploadService.uploadImg(multipartFile, "temp");
        String filename = (String) map.get("filename");
        try {
            FaceEngineUtil faceEngineUtil = new FaceEngineUtil();
            String result = faceEngineUtil.run(filename);
            if (!"false".equals(result)) {
                System.out.println("识别成功");
                System.out.println(result);
                Student student = studentService.getOne(new QueryWrapper<Student>().like("face", result));
                Assert.notNull(student,"识别失败");
                Teacher teacher = teacherService.getById(student.getTeacherId());
                student.setTeacherName(teacher.getName());
                student.setPassword("");
                return Result.success(student, "识别成功");
            }
            return Result.fail("你不是本人哦,识别失败");
        } catch (Exception e) {
            return Result.fail("有异常,识别失败");
        }

    }
}
