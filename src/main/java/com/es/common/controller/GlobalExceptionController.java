package com.es.common.controller;

import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.es.common.util.JsonResult;



@ControllerAdvice
public class GlobalExceptionController {

	@ExceptionHandler(ShiroException.class)
	@ResponseBody
	public com.es.common.util.JsonResult doHandleShiroException(ShiroException e) {
		e.printStackTrace();
		if(e instanceof IncorrectCredentialsException){
			return new com.es.common.util.JsonResult(0,"密码不正确");
		}else if(e instanceof AuthorizationException){
			return new com.es.common.util.JsonResult(0,"您没权限执行此操作");
		}
		return new com.es.common.util.JsonResult(0,e.getMessage());
	}

	@ExceptionHandler(RuntimeException.class)
	@ResponseBody
	public JsonResult doHandleRuntimeException(RuntimeException e) {
		e.printStackTrace();
		return new JsonResult(e);
	}

}
