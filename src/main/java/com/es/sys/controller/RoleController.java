package com.es.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.es.common.util.JsonResult;
import com.es.sys.pojo.Roles;
import com.es.sys.service.RoleService;

@Controller
@RequestMapping("/")
public class RoleController {
	@Autowired
	private RoleService roleServcie;
	
	@RequestMapping("findRoles")
	@ResponseBody
	public JsonResult findRoles(Roles roles) {
		List<Roles> doFindRoles = roleServcie.doFindRoles(roles);
		return new JsonResult(doFindRoles);	
		}
	
	
}
