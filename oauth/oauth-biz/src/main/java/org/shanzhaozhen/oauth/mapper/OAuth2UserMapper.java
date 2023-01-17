package org.shanzhaozhen.oauth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.shanzhaozhen.oauth.pojo.dto.OAuth2UserDTO;
import org.shanzhaozhen.oauth.pojo.entity.OAuth2UserDO;

public interface OAuth2UserMapper extends BaseMapper<OAuth2UserDO> {

    OAuth2UserDO selectUserByUsername(@Param("username") String username);

    Integer countByUsername(@Param("username") String username);

    OAuth2UserDTO getOAuth2UserById(@Param("id") String id);

    OAuth2UserDTO getOAuth2UserByUsername(@Param("username") String username);

    OAuth2UserDTO getOAuth2UserByUserId(@Param("userId") String userId);

    Page<OAuth2UserDTO> getOAuth2UserPage(Page<OAuth2UserDTO> page, @Param("keyword") String keyword);

}
