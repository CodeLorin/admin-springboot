package com.lorin.service.impl;

import com.lorin.common.Result;
import com.lorin.entity.Attendance;
import com.lorin.mapper.AttendanceMapper;
import com.lorin.service.AttendanceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lorin
 * @since 2021-12-19
 */
@Service
public class AttendanceServiceImpl extends ServiceImpl<AttendanceMapper, Attendance> implements AttendanceService {
    @Autowired
    AttendanceMapper attendanceMapper;


}
