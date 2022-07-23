package org.shanzhaozhen.uaa.service.impl;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.common.redis.utils.RedisUtils;
import org.shanzhaozhen.uaa.exception.CaptchaErrorException;
import org.shanzhaozhen.uaa.pojo.dto.CaptchaInfo;
import org.shanzhaozhen.uaa.service.CaptchaService;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class CaptchaServiceImpl implements CaptchaService {

    private final DefaultKaptcha captchaImage;

    private final DefaultKaptcha captchaPhone;

    private final RedisUtils redisUtils;

    @Override
    public void generateCaptchaImage(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("image/jpeg");
        String captcha = captchaImage.createText();
        // TODO: 2022/7/24 保存到redis
//        redisUtils.set(phone, captcha, 300);
        BufferedImage image = captchaImage.createImage(captcha);
        try(ServletOutputStream out = response.getOutputStream()) {
            ImageIO.write(image, "jpg", out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CaptchaInfo generateCaptchaToPhone(String phone) {
        String captcha = captchaPhone.createText();
        // 验证码保存到redis中5分钟有效
        redisUtils.set(phone, captcha, 300);
        return new CaptchaInfo(phone, captcha);
    }

    @Override
    public boolean verifyCaptcha(String key, String rawCode) throws CaptchaErrorException {
        Object o = redisUtils.get(key);
        return rawCode.equals(o);
    }

}
