package org.shanzhaozhen.oauth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.shanzhaozhen.oauth.pojo.dto.OAuth2TokenSettingsDTO;
import org.shanzhaozhen.oauth.pojo.entity.OAuth2TokenSettingsDO;

/**
 * @Author: shanzhaozhen
 * @Date: 2023-01-16
 * @Description: oauth2客户端的token配置项 Mapper 接口
 */
public interface OAuth2TokenSettingsMapper extends BaseMapper<OAuth2TokenSettingsDO> {

    OAuth2TokenSettingsDTO getOAuth2TokenSettingsByRegisteredClientId(@Param("registeredClientId") String registeredClientId);

    void deleteOAuth2TokenSettingsByRegisteredClientId(@Param("registeredClientId") String registeredClientId);

}
