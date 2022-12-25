package org.shanzhaozhen.common.core.utils;

import org.springframework.util.StringUtils;

public class EncryptUtils {

    /**
     * 手机号码前三后四脱敏
     *
     * @param mobile
     * @return
     */
    public static String mobileEncrypt(String mobile) {
        if (!StringUtils.hasText(mobile) || mobile.length() != 11) {
            return mobile;
        }
        return mobile.replaceAll("(\\w{3})\\w*(\\w{4})", "$1****$2");
    }

    /**
     * 身份证脱敏
     * 身份证号的保留前三后四，中间的数为星号  "*"
     *
     * @param identity
     * @return
     */
    public static String identityEncrypt(String identity) {
        if (StringUtils.hasText(identity) || identity.length() != 18) {
            return identity;
        }
        return identity.replaceAll("(\\w{3})\\w*(\\w{4})", "$1****$2");
    }


    /**
     * 姓名脱敏
     * 规则：3个字以内脱敏第一个字，4—6个字脱敏前两个字，大于6个字脱敏第3—6个字
     * 示例：张三(三)、张三水(三水)、长孙无忌(**无忌)、罗斯托罗司机格(罗斯XXXX格)
     *
     * @param name
     * @return
     */
    public static String nameEncrypt(String name) {
        if (StringUtils.hasText(name)) {
            if (name.length() == 2) {
                return name.charAt(0) + "*";//截取name 字符串截取第一个字符，
            } else if (name.length() == 3) {
                return name.charAt(0) + "*" + name.charAt(2);//截取第一个和第三个字符
            } else if (name.length() == 4) {
                return "**" + name.substring(2, 4);//截取第一个和大于第4个字
            } else if (name.length() > 4) {
                return name.substring(0, 2) + "*" + '*' + name.substring(name.length() - 1);//截取第一个和大于第4个字
            }
        }
        return "";
    }

}
