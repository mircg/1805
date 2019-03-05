package com.es.sys.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.es.common.exception.ServiceException;
import com.es.sys.mapper.AdminMapper;
import com.es.sys.mapper.AdminRolesMapper;
import com.es.sys.pojo.Admin;
import com.es.sys.pojo.AdminExample;
import com.es.sys.pojo.AdminExample.Criteria;
import com.es.sys.pojo.AdminRoles;
import com.es.sys.pojo.AdminRolesExample;
import com.es.sys.pojo.Roles;
import com.es.sys.service.AdminService;
import com.es.sys.service.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	private AdminMapper adminMapper;
	@Autowired
	private RoleService roleService;
	@Autowired
	private AdminRolesMapper adminRolesMapper;
	
  
	@Override
	public PageInfo<Admin> findAdminList(Integer pageNum, Integer pageSize) {
	if(pageSize==null) {
		pageSize=10;
	}if(pageNum==null) {
		throw new ServiceException("页面值无效");
	}
		AdminExample adminExample = new AdminExample();
		Criteria createCriteria = adminExample.createCriteria();
		createCriteria.andIdIsNotNull();
		PageHelper.startPage(pageNum, pageSize);
		List<Admin> list = adminMapper.selectByExample(adminExample);
	
		for (Admin admin : list) {
		List<Roles> ls=	roleService.findRoleByAdminId(admin.getId());
		admin.setRoles(ls);
		}
		PageInfo<Admin> pageInfo=new PageInfo<>(list);
		
		return pageInfo;
	}


	@Override
	public Admin findAdminById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void validAdmin(Integer id, Integer valid) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void saveAdmin(Admin admin, Integer[] roleIds) {

		if(StringUtils.isEmpty(admin.getUsername()))
			throw new IllegalArgumentException("用户不能为空");
		if(StringUtils.isEmpty(admin.getName()))
			throw new IllegalArgumentException("员工姓名不能为空");
		if(StringUtils.isEmpty(admin.getPassword()))
			throw new IllegalArgumentException("密码不能为空");
			if(StringUtils.isEmpty(admin.getGender()))
		    	throw new IllegalArgumentException("性别不能为空");
	    	if(StringUtils.isEmpty(admin.getTelphone()))
		    	throw new IllegalArgumentException("手机号不能为空");
	    	if(StringUtils.isEmpty(admin.getEmail()))
		    	throw new IllegalArgumentException("邮箱不能为空"); 
	    	if(roleIds.length==0)
				throw new ServiceException("至少赋予用户一个部门");
		if(admin.getValid() == null) {
			//默认启用
			admin.setValid(1);
		}
		String salt=UUID.randomUUID().toString();
		SimpleHash sh=new SimpleHash(//需要先添加shiro-spring依赖
				"MD5",//algorithmName 加密算法
				admin.getPassword(),//source被加密的对象
				salt, //salt盐值(使用产生的随机字符串)
				5);//hashIterations 加密次数
		admin.setSalt(salt);
		admin.setPassword(sh.toHex());
		admin.setCreatedtime(new Date());
		admin.setModifieduser("admin");
		int rows=0;
		try {
			rows = adminMapper.insert(admin);
			
		
			AdminExample adminExample = new AdminExample();
			Criteria createCriteria = adminExample.createCriteria();
			createCriteria.andUsernameEqualTo(admin.getUsername());
			List<Admin> selectByExample = adminMapper.selectByExample(adminExample);
			Admin admin3 = new Admin();
			
			
			for (Admin admin2 : selectByExample){
				admin3.setId(admin2.getId());	
			}
			for(int roleId : roleIds) {
				
				AdminRoles adminRoles = new AdminRoles();
				adminRoles.setAdminId(admin3.getId());
				adminRoles.setDepartmentId(roleId);
				adminRolesMapper.insert(adminRoles);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("用户名不能重复");
		}
		if(rows==0)
			throw new ServiceException("保存失败");

		
	}

	}
	
	
	


