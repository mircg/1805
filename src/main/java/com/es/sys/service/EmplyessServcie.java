package com.es.sys.service;

import java.util.List;

import com.es.sys.pojo.User;



public interface EmplyessServcie {
	 List<User> findUser();
	void saveUser(User user);

	String findUsers();
	void doDeleteById(Integer id);
	User findUserById(Integer id);
	User doUpdateUser(User user);

	
	

}
