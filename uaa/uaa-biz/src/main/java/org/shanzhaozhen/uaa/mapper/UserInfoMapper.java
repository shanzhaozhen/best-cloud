package org.shanzhaozhen.uaa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.shanzhaozhen.uaa.pojo.dto.UserInfoDTO;
import org.shanzhaozhen.uaa.pojo.entity.UserInfoDO;

public interface UserInfoMapper extends BaseMapper<UserInfoDO> {

    UserInfoDTO getUserInfoById(Long userInfoId);

    UserInfoDTO getUserInfoByUserId(Long userId);

    int deleteByUserId(Long userId);

}
