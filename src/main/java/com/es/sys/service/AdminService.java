package com.es.sys.service;

import java.util.List;

import com.es.sys.pojo.Admin;
import com.github.pagehelper.PageInfo;

public interface AdminService {

	PageInfo<Admin> findAdminList(Integer pageNum, Integer pageSize);
//Integer pageNum, Integer pageSize

	Admin findAdminById(Integer id);

	void validAdmin(Integer id, Integer valid);

	void saveAdmin(Admin admin, Integer[] roleIds);
}
