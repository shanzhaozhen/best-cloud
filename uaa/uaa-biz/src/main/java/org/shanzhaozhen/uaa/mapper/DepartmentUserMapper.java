package org.shanzhaozhen.uaa.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.shanzhaozhen.uaa.pojo.entity.DepartmentUserDO;

public interface DepartmentUserMapper extends BaseMapper<DepartmentUserDO> {

    DepartmentUserDO getDepartmentUserByUserId(@Param("userId") Long userId);

    DepartmentUserDO getDepartmentUserByDepartmentIdAndUserId(@Param("departmentId") Long departmentId, @Param("userId") Long userId);

    void deleteByDepartmentIdAndUserId(@Param("departmentId") Long departmentId, @Param("userId") Long userId);

}
