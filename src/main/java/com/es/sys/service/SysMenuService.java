package com.es.sys.service;

import java.util.List;
import java.util.Map;

import com.es.common.vo.Node;
import com.es.sys.pojo.Menus;


public interface SysMenuService {
	public List<Map<String,Object>>findObjects();
	
	public int deleteObject(Integer id);
	
	public List<Node> findZtreeMenuNodes();
	
	public int insertObject(Menus entity);
	public int updataObject(Menus entity);
}
