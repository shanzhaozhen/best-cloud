package org.shanzhaozhen.uaa.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.common.redis.utils.RedisUtils;
import org.shanzhaozhen.uaa.exception.CaptchaErrorException;
import org.shanzhaozhen.uaa.service.CaptchaService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class CaptchaServiceImpl implements CaptchaService {

    private final RedisUtils redisUtils;

    private final static DefaultIdentifierGenerator defaultIdentifierGenerator = new DefaultIdentifierGenerator();


    @Override
    public String generateCaptchaNumber(String phone) {
        RandomGenerator randomGenerator = new RandomGenerator("0123456789", 4);
        // 保存 redis 十分钟，验证码的有效期
        redisUtils.set(phone, randomGenerator, 60 * 10);
        return randomGenerator.generate();
    }

    @Override
    public void generateCaptchaImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //定义图形验证码的长、宽、验证码字符数、干扰元素个数
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(200, 100, 4, 20);
        String captcha = circleCaptcha.getCode();
        // 生成雪花ID，作为key
        String key = defaultIdentifierGenerator.nextId(captcha).toString();
        // 记录验证码到 redis，用于校验
        redisUtils.set(key, captcha, 60 * 10);
        circleCaptcha.write(response.getOutputStream());
    }

    @Override
    public boolean verifyCaptcha(String key, String rawCode) throws CaptchaErrorException {
        String captcha = (String) redisUtils.get(key);
        Assert.hasText(captcha, "验证码已过期或无效");
        return captcha.equals(rawCode);
    }
}
