package com.lorin.service.impl;

import com.lorin.entity.Student;
import com.lorin.mapper.StudentMapper;
import com.lorin.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lorin
 * @since 2021-12-18
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

}
