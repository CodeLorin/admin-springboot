package com.lorin.mapper;

import com.lorin.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lorin
 * @since 2021-12-18
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {

}