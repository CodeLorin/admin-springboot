package com.lorin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lorin.aop.LogAnnotation;
import com.lorin.common.Result;
import com.lorin.entity.Attendance;
import com.lorin.entity.AttendanceRecord;
import com.lorin.entity.Student;
import com.lorin.entity.User;
import com.lorin.service.AttendanceRecordService;
import com.lorin.service.AttendanceService;
import com.lorin.service.StudentService;
import com.lorin.service.UserService;
import com.lorin.utils.PageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lorin
 * @since 2021-12-19
 */
@Api(value = "考勤Controller", tags = {"考勤考勤路由接口"})
@RestController
@RequestMapping("/sys/attendance")
public class AttendanceController {
    @Autowired
    AttendanceService attendanceService;
    @Autowired
    PageUtil pageUtil;
    @Autowired
    AttendanceRecordService attendanceRecordService;
    @Autowired
    StudentService studentService;
    @ApiOperation(value = "获取考勤信息")
    @LogAnnotation(module = "考勤", operation = "获取全部考勤信息")
    @PreAuthorize("hasAuthority('sys:attendance:list')")
    @GetMapping("/list")
    public Result list(String query) {
        Page<Attendance> attendanceData = attendanceService.page(pageUtil.getPage(), new QueryWrapper<Attendance>().like(StringUtils.isNotBlank(query), "name", query));
        return Result.success(attendanceData, "获取考勤信息成功");
    }
    @PreAuthorize("hasAuthority('sys:attendance:record')")
    @ApiOperation(value = "获取考勤记录信息")
    @LogAnnotation(module = "考勤", operation = "获取全部考勤记录信息")
    @GetMapping("/list/record")
    public Result listRecord(String query) {
        Page<AttendanceRecord> attendanceRecordData = attendanceRecordService.page(pageUtil.getPage(), new QueryWrapper<AttendanceRecord>().like(StringUtils.isNotBlank(query), "student_name", query));
        attendanceRecordData.getRecords().forEach(a->{
            Integer id = a.getAttendanceId();
            Attendance attendance = attendanceService.getById(id);
            a.setAttendanceName(attendance.getName());
        });
        return Result.success(attendanceRecordData, "获取考勤记录成功");
    }
    @PreAuthorize("hasAuthority('sys:attendance:list')")
    @ApiOperation(value = "获取选中考勤信息")
    @LogAnnotation(module = "考勤", operation = "获取选中考勤信息")
    @GetMapping("/info/{id}")
    public Result info(@PathVariable("id") Long id) {
        Attendance attendance = attendanceService.getById(id);
        Assert.notNull(attendance, "找不到该考勤");
        return Result.success(attendance, "获取考勤信息成功");
    }
    @PreAuthorize("hasAuthority('sys:attendance:save')")
    @ApiOperation(value = "增加考勤信息")
    @LogAnnotation(module = "考勤", operation = "增加考勤信息")
    @PostMapping("/add")
    public Result add(@Validated @RequestBody Attendance attendance) {
        attendanceService.save(attendance);
        return Result.success("", "增加考勤成功");
    }
    @PreAuthorize("hasAuthority('sys:attendance:update')")
    @ApiOperation(value = "更新考勤信息")
    @LogAnnotation(module = "考勤", operation = "更新考勤信息")
    @PutMapping("/update")
    public Result update(@Validated @RequestBody Attendance attendance) {

        attendanceService.updateById(attendance);
        return Result.success("", "更新考勤成功");
    }
    @PreAuthorize("hasAuthority('sys:attendance:delete')")
    @ApiOperation(value = "删除考勤信息")
    @LogAnnotation(module = "考勤", operation = "删除考勤")
    @Transactional
    @DeleteMapping("/delete")
    public Result delete(@RequestBody Long[] ids) {
        attendanceService.removeByIds(Arrays.asList(ids));
        return Result.success("", "删除考勤成功");
    }
    @ApiOperation(value = "获取考勤信息")
    @LogAnnotation(module = "考勤", operation = "获取全部考勤信息")
    @GetMapping("/list/get")
    public Result listGet(@RequestParam("id") Integer id) {
        Student student = studentService.getById(id);
        Assert.notNull(student,"学生不存在");
        ArrayList<Attendance> list = new ArrayList<>();
        List<Attendance> attendance = attendanceService.list();
        attendance.forEach(e->{
            Integer attendanceId = e.getId();
            AttendanceRecord record = attendanceRecordService.getOne(new QueryWrapper<AttendanceRecord>().eq("attendance_id", attendanceId).eq("student_name", student.getName()));
            if (record==null){
                list.add(e);
            }

        });
        return Result.success(list, "获取考勤信息成功");
    }
    @ApiOperation(value = "开始考勤信息")
    @LogAnnotation(module = "考勤", operation = "开始考勤")
    @PostMapping("/record")
    public Result record(@RequestParam("id") Integer id,@RequestParam("attendanceId")Integer attendanceId) {
        System.out.println(id+"|"+attendanceId);
        Student student = studentService.getById(id);
        Assert.notNull(student,"学生不存在");
        Attendance attendance = attendanceService.getById(attendanceId);
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime publishTime = attendance.getPublishTime();
        Duration duration = Duration.between(now, publishTime);
        Integer limitTime = attendance.getLimitTime();
        long minutes = duration.toMinutes();
        if (Math.abs(minutes)>limitTime){
            return Result.fail("签到失败,签到时间已经过啦");
        }
        AttendanceRecord attendanceRecord = new AttendanceRecord();
        attendanceRecord.setAttendanceTime(now);

        attendanceRecord.setStudentName(student.getName());
        attendanceRecord.setAttendanceId(attendance.getId());
        attendanceRecordService.save(attendanceRecord);
        return Result.success("", "考勤成功");
    }
}
