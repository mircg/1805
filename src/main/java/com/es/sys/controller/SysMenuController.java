package com.es.sys.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.es.common.util.JsonResult;
import com.es.sys.pojo.Menus;
import com.es.sys.service.SysMenuService;




@RequestMapping("/menu")
@Controller
public class SysMenuController {
	
	@Autowired
	private SysMenuService sysMenuService;
	

	@RequestMapping("/doMenuListUI")
	public String doMenuListUI() {
		return "sys/menu_list";
	}
	
	@RequestMapping("/doFindObjects")
	@ResponseBody
	public JsonResult doFindObjects() {
		List<Map<String, Object>> findObjects = sysMenuService.findObjects();
		System.out.println(findObjects);
		return new JsonResult(findObjects);
	}
	
	@RequestMapping(value="/doDeleteObject/{id}")
	@ResponseBody
	public JsonResult doDeleteObject(
			@PathVariable Integer id) {
		sysMenuService.deleteObject(id);
		return new JsonResult("delete ok");
	}
	
	@RequestMapping(value="/doMenuEditUI")
	public String doMenuEditUI() {
		return "menu_edit";
	}
	@RequestMapping(value="/doFindZtreeMenuNodes" , 
			produces="application/json;charset=utf-8")
	@ResponseBody
	public JsonResult doFindZtreeMenuNodes() {
		return new JsonResult(sysMenuService.findZtreeMenuNodes());
	}
	

	@RequestMapping(value="/doSaveObject",
			produces="application/json;charset=utf-8")
	@ResponseBody
	public JsonResult doSaveObject(Menus entity) {
		System.out.println(entity);
		sysMenuService.insertObject(entity);
		return new JsonResult("insert ok");
	}
	
	@RequestMapping(value="/doUpdateObject",
			produces="application/json;charset=utf-8")
	@ResponseBody
	public JsonResult doUpdateObject(Menus entity) {
		sysMenuService.updataObject(entity);
		return new JsonResult("updata ok");
	}
}
