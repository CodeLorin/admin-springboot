package com.lorin.controller;

import cn.hutool.core.map.MapUtil;
import com.google.code.kaptcha.Producer;
import com.lorin.common.Const;
import com.lorin.common.Result;
import com.lorin.utils.RedisUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * TODO
 *
 * @author lorin
 * @date 2022/6/22 23:48
 */
@Api(value = "验证码Controller", tags = {"获取验证码接口"})
@RestController
public class AuthController {
    @Autowired
    Producer producer;
    @Autowired
    RedisUtil redisUtil;

    @GetMapping("/captcha")
    public Result captcha() throws IOException {

        String key = UUID.randomUUID().toString();
        String code = producer.createText();


        BufferedImage image = producer.createImage(code);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream);

        BASE64Encoder encoder = new BASE64Encoder();
        String str = "data:image/jpeg;base64,";

        String base64Img = str + encoder.encode(outputStream.toByteArray());

        redisUtil.hset(Const.CAPTCHA_KEY, key, code, 120);

        return Result.success(
                MapUtil.builder()
                        .put("token", key)
                        .put("captchaImg", base64Img)
                        .build()

                , "获取验证码成功");
    }

}
