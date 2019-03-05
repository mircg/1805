package com.es.sys.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.es.common.exception.ServiceException;
import com.es.sys.mapper.AdminRolesMapper;
import com.es.sys.pojo.AdminRoles;
import com.es.sys.pojo.AdminRolesExample;
import com.es.sys.pojo.Roles;
import com.es.sys.pojo.RolesExample;
import com.es.sys.pojo.RolesExample.Criteria;
import com.es.sys.service.RoleService;
import com.es.sys.mapper.RolesMapper;
@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private AdminRolesMapper adminRolesMapper;
	@Autowired
	private RolesMapper rolesMapper;

	@Override
	public List<Roles> findRoleByAdminId(Integer id) {
		if(id == null || id<1) {
			throw new IllegalArgumentException("参数错误");
		}
		System.out.println(id);
		AdminRolesExample adminRolesExample = new AdminRolesExample();
		com.es.sys.pojo.AdminRolesExample.Criteria createCriteria = adminRolesExample.createCriteria();
		createCriteria.andAdminIdEqualTo(id);//条件如果实例的id 等于用户id
		
		 List<AdminRoles> selectByExample = adminRolesMapper.selectByExample(adminRolesExample);
		 System.out.println(selectByExample);
		if(selectByExample.size()==0) {
			throw new IllegalArgumentException("该员工没有部门");
		}
		List<Roles> roleList=new ArrayList<>();
		for (AdminRoles adminRoles : selectByExample) {
			Roles role=rolesMapper.selectByPrimaryKey(adminRoles.getDepartmentId());
			roleList.add(role);
			
		}
		return roleList;
		
	}

	@Override
	public List<Roles> doFindRoles(Roles roles) {
		RolesExample rolesExample = new RolesExample();
	Criteria createCriteria = rolesExample.createCriteria();
	createCriteria.andIdIsNotNull();
	List<Roles> selectByExample = rolesMapper.selectByExample(rolesExample);
	
		return selectByExample;
	}

}
