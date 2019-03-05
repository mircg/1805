package com.es.sys.mapper;



import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.es.sys.pojo.AdminRoles;
import com.es.sys.pojo.AdminRolesExample;

public interface AdminRolesMapper {
    int countByExample(AdminRolesExample example);

    int deleteByExample(AdminRolesExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AdminRoles record);

    int insertSelective(AdminRoles record);

    List<AdminRoles> selectByExample(AdminRolesExample example);

    AdminRoles selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AdminRoles record, @Param("example") AdminRolesExample example);

    int updateByExample(@Param("record") AdminRoles record, @Param("example") AdminRolesExample example);

    int updateByPrimaryKeySelective(AdminRoles record);

    int updateByPrimaryKey(AdminRoles record);

	List<Integer> findRoleIdsByUserId(Integer id);
}