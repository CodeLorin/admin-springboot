package com.lorin;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lorin.entity.Student;
import com.lorin.service.StudentService;
import com.lorin.utils.FaceEngineUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class WebBackendSysApplicationTests {
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    StudentService studentService;
    @Test
    public void test2(){
            FaceEngineUtil faceEngineUtil = new FaceEngineUtil();
            String run = faceEngineUtil.run("face2.jpg");
            if (!"false".equals(run)) {
                System.out.println(run);
                System.out.println("识别成功");
            }

//        }
    }
    @Test
    public void test() {
        System.out.println(bCryptPasswordEncoder.encode("lorin"));
    }
    @Test
    void test3(){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime now1 = LocalDateTime.now();
        Duration duration = Duration.between(now, now1);
        long seconds = duration.getSeconds();
        System.out.println(seconds);
    }
}
