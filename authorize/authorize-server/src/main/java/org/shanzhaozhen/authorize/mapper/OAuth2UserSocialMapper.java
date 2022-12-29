package org.shanzhaozhen.authorize.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.shanzhaozhen.authorize.pojo.dto.OAuth2UserSocialDTO;
import org.shanzhaozhen.authorize.pojo.entity.OAuth2UserSocialDO;

import java.util.List;


/**
 * @Author: shanzhaozhen
 * @Date: 2022-09-02
 * @Description:
 */
public interface OAuth2UserSocialMapper extends BaseMapper<OAuth2UserSocialDO> {

    List<OAuth2UserSocialDTO> getOAuth2UserSocialByUserId(@Param("userId") String userId);

    OAuth2UserSocialDO getOAuth2UserSocialByIdentityAndTypeAndIdentifier(@Param("identityType") String identityAndType, @Param("identifier") String identifier);

    OAuth2UserSocialDO getOAuth2UserSocialByUserIdAndIdentityType(@Param("userId") String userId, @Param("identityType") String identityAndType);

}
