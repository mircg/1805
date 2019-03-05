package com.es.sys.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.es.common.exception.ServiceException;
import com.es.sys.mapper.UserMapper;
import com.es.sys.pojo.User;
import com.es.sys.pojo.UserExample;
import com.es.sys.pojo.UserExample.Criteria;
import com.es.sys.service.EmplyessServcie;




@Service
public class EmplyessServcieImpl implements EmplyessServcie {
	@Autowired
	private UserMapper userMapper;

	@Override
	public List<User> findUser() {
		UserExample userExample = new UserExample();
		Criteria createCriteria = userExample.createCriteria();
		createCriteria.andUserIdIsNotNull();
		List<User> list=userMapper.selectByExample(userExample);
		
		return list;
	}

	@Override
	public void saveUser(User user) {
		if(user==null)
	    	throw new IllegalArgumentException("保存对象不能为空");
	    	if(StringUtils.isEmpty(user.getName()))
	    	throw new IllegalArgumentException("用户不能为空");
	    	if(StringUtils.isEmpty(user.getPost()))
	    	throw new IllegalArgumentException("职位不能为空");
	    	if(StringUtils.isEmpty(user.getGender()))
		    	throw new IllegalArgumentException("性别不能为空");
	    	if(StringUtils.isEmpty(user.getTelphone()))
		    	throw new IllegalArgumentException("手机号不能为空");
	    	if(StringUtils.isEmpty(user.getEmail()))
		    	throw new IllegalArgumentException("邮箱不能为空");
	    	if(StringUtils.isEmpty(user.getDepartment()))
		    	throw new IllegalArgumentException("部门不能为空");
	    
	    	int rows=0;
			try {
				user.setCreatedtime(new Date());
				user.setModifieduser("admin");
				rows = userMapper.insert(user);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException("系统故障，操作失败");
			}
			if(rows==0)
				throw new ServiceException("保存失败");
	    	
		
	}

	@Override
	public User findUserById(Integer id) {
		if(id==null||id<0) {
			throw new IllegalArgumentException("id值不合法");
		}
		
		
		if(id== null) {
			throw new ServiceException("用户已经不存在");
		}
		User user=	userMapper.selectByPrimaryKey(id);
		
		return user;
		
	}

	@Override
	public String findUsers() {
		
		return null;
	}

	@Override
	public void doDeleteById(Integer id) {
		if(id == null || id<1) {
			throw new IllegalArgumentException("参数异常");
		}
		userMapper.deleteByPrimaryKey(id);
		
		
	}

	@Override
	public User doUpdateUser(User user) {
		if(user==null)
    	throw new IllegalArgumentException("保存对象不能为空");
    	if(StringUtils.isEmpty(user.getName()))
    	throw new IllegalArgumentException("用户不能为空");
    	if(StringUtils.isEmpty(user.getPost()))
    	throw new IllegalArgumentException("职位不能为空");
    	if(StringUtils.isEmpty(user.getGender()))
	    	throw new IllegalArgumentException("性别不能为空");
    	if(StringUtils.isEmpty(user.getTelphone()))
	    	throw new IllegalArgumentException("手机号不能为空");
    	if(StringUtils.isEmpty(user.getEmail()))
	    	throw new IllegalArgumentException("邮箱不能为空");
    	if(StringUtils.isEmpty(user.getDepartment()))
	    	throw new IllegalArgumentException("部门不能为空");
    
    	int rows=0;
		try {
			user.setCreatedtime(new Date());
			user.setModifieduser("admin");
			rows = userMapper.updateByPrimaryKeySelective(user);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("系统故障，操作失败");
		}
		if(rows==0)
			throw new ServiceException("保存失败");
		return user;
	}


}
