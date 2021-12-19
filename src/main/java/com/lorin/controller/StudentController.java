package com.lorin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lorin.aop.LogAnnotation;
import com.lorin.common.Const;
import com.lorin.common.Result;
import com.lorin.common.dto.TeacherDto;
import com.lorin.entity.Student;
import com.lorin.entity.Teacher;
import com.lorin.service.StudentService;
import com.lorin.service.TeacherService;
import com.lorin.utils.PageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lorin
 * @since 2021-12-18
 */
@Api(value = "学生Controller", tags = {"学生路由接口"})
@RestController
@RequestMapping("/sys/student")
public class StudentController {
    @Autowired
    StudentService studentService;
    @Autowired
    PageUtil pageUtil;
    @Autowired
    TeacherService teacherService;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @ApiOperation(value = "获取全部学生信息")
    @LogAnnotation(module = "学生", operation = "获取全部学生信息")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:student:list')")
    public Result list(String query) {
        Page<Student> studentData = studentService.page(pageUtil.getPage(), new QueryWrapper<Student>().like(StringUtils.isNotBlank(query), "name", query));
        studentData.getRecords().forEach(student -> {
            Teacher teacher = teacherService.getById(student.getTeacherId());
            student.setTeacherName(teacher.getName());
            student.setPassword("");
        });
        return Result.success(studentData, "获取全部学生信息成功");
    }

    @ApiOperation(value = "获取选中学生信息")
    @LogAnnotation(module = "学生", operation = "获取选中学生信息")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('sys:student:list')")
    public Result info(@PathVariable("id") Long id) {
        Student student = studentService.getById(id);
        student.setPassword("");
        Integer teacherId = student.getTeacherId();
        Teacher teacher = teacherService.getById(teacherId);
        student.setTeacherName(teacher.getName());
        Assert.notNull(student, "找不到该学生");
        return Result.success(student, "获取学生信息成功");
    }

    @ApiOperation(value = "获取老师信息")
    @LogAnnotation(module = "老师", operation = "获取老师信息")
    @GetMapping("/info/teacher")
    @PreAuthorize("hasAuthority('sys:student:list')")
    public Result teacherInfo() {
        List<TeacherDto> teacherDtos = new ArrayList<>();
        List<Teacher> list = teacherService.list();
        list.forEach(e -> {
            TeacherDto teacherDto = new TeacherDto();
            teacherDto.setId(e.getId());
            teacherDto.setName(e.getName());
            teacherDtos.add(teacherDto);
        });
        return Result.success(teacherDtos, "获取老师信息成功");
    }

    @ApiOperation(value = "增加学生信息")
    @LogAnnotation(module = "学生", operation = "增加学生信息")
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('sys:student:save')")
    public Result add(@Validated @RequestBody Student student) {
        String pwd = passwordEncoder.encode(Const.DEFAULT_PASSWORD);
        student.setPassword(pwd);
        studentService.save(student);
        return Result.success("", "增加学生成功");
    }

    @ApiOperation(value = "更新学生信息")
    @LogAnnotation(module = "学生", operation = "更新学生信息")
    @PutMapping("/update")
    @PreAuthorize("hasAuthority('sys:student:update')")
    public Result update(@Validated @RequestBody Student student) {
        String pwd = passwordEncoder.encode(Const.DEFAULT_PASSWORD);
        student.setPassword(pwd);
        studentService.updateById(student);
        return Result.success("", "更新学生成功");
    }

    @ApiOperation(value = "删除学生信息")
    @LogAnnotation(module = "学生", operation = "删除学生")
    @Transactional
    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('sys:student:delete')")
    public Result delete(@RequestBody Long[] ids) {
        studentService.removeByIds(Arrays.asList(ids));
        return Result.success("", "删除学生成功");
    }
    @ApiOperation(value = "获取学生人脸信息")
    @LogAnnotation(module = "学生", operation = "获取人脸信息")
    @GetMapping("/info/face")
    public Result info() {
        List<String> faces = studentService.list(Wrappers.<Student>lambdaQuery().select(Student::getFace)).stream().map(Student::getFace).collect(Collectors.toList());
        for (String face : faces) {
            String[] split = face.split("/");
            for (String s : split) {
                System.out.println(s);

            }
        }
        return Result.success(faces, "获取学生人脸信息成功");
    }
}
