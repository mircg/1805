package com.es.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.es.common.util.JsonResult;
import com.es.sys.pojo.User;
import com.es.sys.service.EmplyessServcie;



@Controller
@RequestMapping("/")
public class EmplyessController {
	@Autowired
	private EmplyessServcie emplyessService;
	@RequestMapping(value="doEmplyessList",produces="application/json;charset=utf-8")
	@ResponseBody
	public JsonResult doEmplyessList(User user) {
		return new JsonResult(emplyessService.findUser());
		
	}
	
	@RequestMapping(value="/doSaveUser",
			produces="application/json;charset=utf-8")
	@ResponseBody
	public JsonResult doSaveUser(User user) {
		emplyessService.saveUser(user);
		return new JsonResult("save ok");
	}

		
	@RequestMapping(value="/doFindUsers",
			produces="application/json;charset=utf-8")
	@ResponseBody
	public JsonResult doFindUsers() {
		return new JsonResult(emplyessService.findUsers());
	}
	@RequestMapping(value="/doDeleteById",
			produces="application/json;charset=utf-8")
	@ResponseBody
	public JsonResult doDeleteById(Integer id) {
		emplyessService.doDeleteById(id);
		return new JsonResult("删除成功");
		
	}
	
	
	@RequestMapping(value="/doFindUserById",
			produces="application/json;charset=utf-8")
	@ResponseBody
	public JsonResult doFindUserById(Integer id) {
		User findUserById = emplyessService.findUserById(id);
		return new JsonResult(findUserById);
		
	}
	
	
	
	@RequestMapping(value="/doUpdateUser",
			produces="application/json;charset=utf-8")
	@ResponseBody
	public JsonResult doUpdateUser(User user) {
		User updateUser = emplyessService.doUpdateUser(user);
		return new JsonResult(updateUser);
		
	}
}

