package com.lorin.mapper;

import com.lorin.entity.Attendance;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lorin
 * @since 2021-12-19
 */
@Mapper
public interface AttendanceMapper extends BaseMapper<Attendance> {

    List<Attendance> getAttendanceByUserName(String username);
}
