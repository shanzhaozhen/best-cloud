package org.shanzhaozhen.uaa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.shanzhaozhen.uaa.pojo.dto.UserInfoDTO;
import org.shanzhaozhen.uaa.pojo.entity.UserInfoDO;

public interface UserInfoMapper extends BaseMapper<UserInfoDO> {

    UserInfoDTO getUserInfoById(@Param("userInfoId") Long userInfoId);

    UserInfoDTO getUserInfoByUserId(@Param("userId") String userId);

    int deleteByUserId(@Param("userId") String userId);

}
