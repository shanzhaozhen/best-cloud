package org.shanzhaozhen.authorize.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.shanzhaozhen.authorize.pojo.dto.OAuth2RedirectUriDTO;
import org.shanzhaozhen.authorize.pojo.entity.OAuth2RedirectUriDO;

import java.util.List;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-06-17
 * @Description: oauth2客户端重定向uri表 Mapper 接口
 */
public interface OAuth2RedirectUriMapper extends BaseMapper<OAuth2RedirectUriDO> {

    List<OAuth2RedirectUriDTO> getOAuth2RedirectUrisByClientId(@Param("clientId") String clientId);

    void deleteOAuth2RedirectUrisByClientId(@Param("clientId") String clientId);

}
