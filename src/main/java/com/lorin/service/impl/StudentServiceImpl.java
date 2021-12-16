package com.lorin.service.impl;

import com.lorin.entity.Student;
import com.lorin.mapper.StudentMapper;
import com.lorin.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lorin
 * @since 2021-12-15
 */
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public boolean registerStudent() {
        Student student = studentMapper.selectById(1);
        System.out.println(student);
        return false;
    }

}
