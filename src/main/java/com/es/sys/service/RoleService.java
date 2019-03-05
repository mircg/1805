package com.es.sys.service;

import java.util.List;

import com.es.sys.pojo.Roles;

public interface RoleService {

	List<Roles> findRoleByAdminId(Integer id);

	List<Roles> doFindRoles(Roles roles);



}
