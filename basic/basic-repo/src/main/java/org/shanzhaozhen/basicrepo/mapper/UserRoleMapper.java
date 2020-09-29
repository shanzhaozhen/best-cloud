package org.shanzhaozhen.basicrepo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.shanzhaozhen.basiccommon.domain.sys.UserRoleDO;

public interface UserRoleMapper extends BaseMapper<UserRoleDO> {

    @Delete("delete from sys_user_role where user_id = #{userId}")
    int deleteByUserId(Long userId);

}
