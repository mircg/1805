package com.es.sys.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.es.common.util.JsonResult;
import com.es.common.vo.AdminRoleVo;
import com.es.sys.pojo.Admin;
import com.es.sys.pojo.AdminRoles;
import com.es.sys.pojo.Roles;
import com.es.sys.service.AdminService;
import com.github.pagehelper.PageInfo;




@Controller
@RequestMapping("/")
public class AdminController {
	@Autowired
	private AdminService adminService;

	/**
	 * 
	 * 登录
	 * @param username
	 * @param password
	 * @param isRemember
	 * @return
	 */
	@RequestMapping(value= {"doLogin"},produces= {"application/json;charset=utf-8"})
	@ResponseBody
	public JsonResult doLogin(String username,String password,Boolean isRemember) {
		//对用户身份进行认证
		//1.获取主体对象(对此对象进行认证)
		Subject subject = SecurityUtils.getSubject();
		//2.提交用户信息
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		//是否记住密码
		token.setRememberMe(isRemember);
		subject.login(token);
		return new JsonResult("登录成功");
	}
	@RequestMapping(value= {"findAdminList"},produces= {"application/json; charset=UTF-8"})
	@ResponseBody
	//Integer pageNum,Integer  pageSize
	public JsonResult findAdminList(Integer pageNum,Integer  pageSize) {
		return new JsonResult(adminService.findAdminList(pageNum, pageSize));


	}
	
	@RequestMapping(value="doSaveAdmin",produces="application/json;charset=utf-8")
	@ResponseBody
	public JsonResult doSaveAdmin(@RequestBody AdminRoleVo param) {
		adminService.saveAdmin(param.getAdmin(),param.getRoleIds());
		System.out.println(param);
		return new JsonResult("save ok");
		
	}
	
	
	@RequestMapping(value="doFindAdminById",produces="application/json;charset=utf-8")
	@ResponseBody
	public JsonResult doFindAdminById( Integer id) {
		Admin findAdminById = adminService.findAdminById(id);
		return new JsonResult(findAdminById);
	}
	@RequestMapping(value="doValidAdmin",produces="application/json;charset=utf-8")
	@ResponseBody
	public JsonResult doValidAdmin(Integer id, Integer valid) {
		 adminService.validAdmin(id, valid);
		return new JsonResult("update ok");
	}
	
	@RequestMapping(value="doFindCurrentAdmin",produces="application/json;charset=utf-8")
	@ResponseBody
	public JsonResult doFindCurrentAdmin() {
		Admin admin = (Admin) SecurityUtils.getSubject().getPrincipal();
		return new JsonResult(adminService.findAdminById(admin.getId()));
	}


}
