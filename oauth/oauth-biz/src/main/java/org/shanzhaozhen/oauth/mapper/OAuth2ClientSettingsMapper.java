package org.shanzhaozhen.oauth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.shanzhaozhen.oauth.pojo.dto.OAuth2ClientSettingsDTO;
import org.shanzhaozhen.oauth.pojo.entity.OAuth2ClientSettingsDO;

/**
 * @Author: shanzhaozhen
 * @Date: 2023-01-16
 * @Description: oauth2客户端配置 Mapper 接口
 */
public interface OAuth2ClientSettingsMapper extends BaseMapper<OAuth2ClientSettingsDO> {

    OAuth2ClientSettingsDTO getOAuth2ClientSettingsByRegisteredClientId(@Param("registeredClientId") String registeredClientId);

    void deleteOAuth2ClientSettingsByRegisteredClientId(@Param("registeredClientId") String registeredClientId);

}
