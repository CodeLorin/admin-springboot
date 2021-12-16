package com.lorin;

import com.lorin.mapper.StudentMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WebBackendSysApplicationTests {
    @Autowired
    private StudentMapper studentMapper;

    @Test
    void contextLoads() {
        studentMapper.selectById(1);
    }

}
