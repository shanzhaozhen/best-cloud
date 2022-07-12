package org.shanzhaozhen.uaa.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.shanzhaozhen.uaa.pojo.entity.DepartmentUserDO;

public interface DepartmentUserMapper extends BaseMapper<DepartmentUserDO> {

    DepartmentUserDO getDepartmentUserByDepartmentIdAndUserId(Long userId, Long userId1);

    void deleteByDepartmentIdAndUserId(Long departmentId, Long userId);

}
