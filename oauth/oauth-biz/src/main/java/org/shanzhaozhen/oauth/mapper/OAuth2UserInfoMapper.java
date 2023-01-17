package org.shanzhaozhen.oauth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.shanzhaozhen.oauth.pojo.dto.OAuth2UserInfoDTO;
import org.shanzhaozhen.oauth.pojo.entity.OAuth2UserInfoDO;

public interface OAuth2UserInfoMapper extends BaseMapper<OAuth2UserInfoDO> {

    OAuth2UserInfoDTO getOAuth2UserInfoById(@Param("userInfoId") String userInfoId);

    OAuth2UserInfoDTO getOAuth2UserInfoByUserId(@Param("userId") String userId);

    int deleteByUserId(@Param("userId") String userId);

}
