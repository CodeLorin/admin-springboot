package com.lorin;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lorin.common.Const;
import com.lorin.common.Result;
import com.lorin.common.dto.OnlineUserDto;

import com.lorin.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
class WebBackendSysApplicationTests {
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    RedisTemplate redisTemplate;



    @Test
    public void test() {
        System.out.println(bCryptPasswordEncoder.encode("123456"));
    }

    @Test
    void test3() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime now1 = LocalDateTime.now();
        Duration duration = Duration.between(now, now1);
        long seconds = duration.getSeconds();
        System.out.println(seconds);
    }

    public <T> T getCacheObject(final String key) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    @Test
    void test4() {
        ArrayList<OnlineUserDto> onlineUserList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        Collection<String> keys = redisTemplate.keys(Const.REDIS_HEADER + "*");
        ArrayList<OnlineUserDto> onlineUserDtos = new ArrayList<>();
        for (String key : keys) {
            Object onlineUser = redisTemplate.opsForValue().get(key);
            OnlineUserDto onlineUserDto = mapper.convertValue(onlineUser, OnlineUserDto.class);
            onlineUserDtos.add(onlineUserDto);
        }

        System.out.println(Result.success(onlineUserList, "获取redis"));
    }
}
