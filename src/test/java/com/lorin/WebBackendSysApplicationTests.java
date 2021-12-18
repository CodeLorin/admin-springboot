package com.lorin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class WebBackendSysApplicationTests {
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void test() {
        System.out.println(bCryptPasswordEncoder.encode("lorin"));
    }
}
