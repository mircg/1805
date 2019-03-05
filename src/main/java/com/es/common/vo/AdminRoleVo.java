package com.es.common.vo;

import java.util.Arrays;

import com.es.sys.pojo.Admin;



public class AdminRoleVo {
	private Admin admin;
	private Integer[] roleIds;
	public Admin getAdmin() {
		return admin;
	}
	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	public Integer[] getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(Integer[] roleIds) {
		this.roleIds = roleIds;
	}
	@Override
	public String toString() {
		return "AdminRoleVo [admin=" + admin + ", roleIds=" + Arrays.toString(roleIds) + "]";
	}

}
