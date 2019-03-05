package com.es.sys.mapper;

import com.es.common.vo.Node;
import com.es.sys.pojo.Menus;
import com.es.sys.pojo.MenusExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface MenusMapper {
    int countByExample(MenusExample example);

    int deleteByExample(MenusExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Menus record);

    int insertSelective(Menus record);

    List<Menus> selectByExample(MenusExample example);

    Menus selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Menus record, @Param("example") MenusExample example);

    int updateByExample(@Param("record") Menus record, @Param("example") MenusExample example);

    int updateByPrimaryKeySelective(Menus record);

    int updateByPrimaryKey(Menus record);

	int updataObject(Menus entity);

	int insertObject(Menus entity);

	List<Node> findZtreeMenuNodes();

	int deleeteObject(Integer id);

	int getChildCount(Integer id);

	List<Map<String, Object>> findObjects();
	
	 List<String> findPermissions(@Param("menuIds")Integer... menuIds);
}