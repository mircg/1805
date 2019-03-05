package com.es.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.es.common.exception.ServiceException;
import com.es.common.vo.Node;
import com.es.sys.mapper.MenusMapper;
import com.es.sys.mapper.RoleMenusMapper;
import com.es.sys.pojo.Menus;
import com.es.sys.service.SysMenuService;


@Service
public class SysMenuServiceImpl implements SysMenuService {
	@Autowired
	private MenusMapper sysMenuDao;
	@Autowired
	private RoleMenusMapper sysRoleMenuDao;
	
	@Override
	public List<Map<String, Object>> findObjects() {
		return sysMenuDao.findObjects();
	}
	
	
	@Override
	public int deleteObject(Integer id) {
		//1.参数有效性验证
		if(id == null || id<=0) {
			throw new ServiceException("请先选择");
		}
		//2.基于id统计子菜单的个数，并进行业务验证
		int count = sysMenuDao.getChildCount(id);
		if(count>0) {
			throw new ServiceException("请先删除子菜单");
		}
		
		//3.删除菜单自身信息
		int rows = sysMenuDao.deleeteObject(id);
		if(rows == 0) {
			throw new ServiceException("此菜单可能已经不存在了");
		}
		//4.删除关联数据
		sysRoleMenuDao.deleteByPrimaryKey(id);
		
		return rows;
	}

	public List<Node> findZtreeMenuNodes() {
	System.out.println();
		return sysMenuDao.findZtreeMenuNodes();
	}


	@Override
	public int insertObject(Menus entity) {
		//1.参数验证
		if(entity == null) {
			throw new ServiceException("保存对象不能为空");
		}
		if(StringUtils.isEmpty(entity.getName())) {
			throw new ServiceException("菜单名不能为空");
		}
		int rows;
		try {
			rows = sysMenuDao.insertObject(entity);
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("保存失败");
		}
		return rows;
	}

	
	@Override
	public int updataObject(Menus entity) {
		//1.参数验证
			System.out.println(entity);
				if(entity == null) {
					throw new ServiceException("保存对象不能为空");
				}
				if(StringUtils.isEmpty(entity.getName())) {
					throw new ServiceException("菜单名不能为空");
				}
				int rows;
				try {
					rows = sysMenuDao.updataObject(entity);
				}catch (Exception e) {
					e.printStackTrace();
					throw new ServiceException("修改失败");
				}
				if(rows==0) {
					throw new ServiceException("可能数据已经存在");
				}
				return rows;
	}


}

