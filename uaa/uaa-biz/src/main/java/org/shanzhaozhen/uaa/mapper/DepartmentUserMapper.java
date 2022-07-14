package org.shanzhaozhen.uaa.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.shanzhaozhen.uaa.pojo.entity.DepartmentUserDO;

public interface DepartmentUserMapper extends BaseMapper<DepartmentUserDO> {

    DepartmentUserDO getDepartmentUserByUserId(@Param("userId") String userId);

    DepartmentUserDO getDepartmentUserByDepartmentIdAndUserId(@Param("departmentId") String departmentId, @Param("userId") String userId);

    void deleteByDepartmentIdAndUserId(@Param("departmentId") String departmentId, @Param("userId") String userId);

    void deleteByDepartmentId(String departmentId);

}
