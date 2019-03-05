package com.es.sys.service.realm;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.es.common.exception.ServiceException;
import com.es.sys.mapper.AdminMapper;
import com.es.sys.mapper.AdminRolesMapper;
import com.es.sys.mapper.MenusMapper;
import com.es.sys.mapper.RoleMenusMapper;
import com.es.sys.pojo.Admin;
import com.es.sys.pojo.AdminRoles;



/**
 * Shiro框架中核心业务组件之一
 * 通过此对象可以完成数据业务的获取以及封装
 * @author Administrator
 *
 */
@Service
public class ShiroUserRealm extends AuthorizingRealm {

	@Autowired
	private AdminMapper adminMapper;
	@Autowired
	private AdminRolesMapper adminRolesMapper;
	@Autowired
	private MenusMapper menusMaper;
	@Autowired
	private RoleMenusMapper roleMenusMapper;
	/**
	 * 设置凭证匹配器(CredentialsMatcher)
	 * 
	 */
	@Override
	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
		HashedCredentialsMatcher cMatcher = new HashedCredentialsMatcher();
		cMatcher.setHashAlgorithmName("MD5");
		//设置加密的次数(这个次数应该与保存密码时那个加密次数一致)
		cMatcher.setHashIterations(5);
		super.setCredentialsMatcher(cMatcher);
	}

	/**
	 * 在此方法中完成认证信息的获取以及封装
	 * 
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//1.获取用户身份信息(例如用户名)
		String username = (String) token.getPrincipal();
		//2.基于用户名从数据库查询记录
		Admin admin = adminMapper.findUserByUserName(username);
		//3.对查询结果进行验证,用户不存在会报异常
		if(admin == null) {
			throw new AuthenticationException("此用户不存在");
		}
		if(admin.getValid()==0)
			throw new AuthenticationException("此用户已被禁用");
		//4.对数据库查询出的相关信息进行封装
		ByteSource credentialsSalt = ByteSource.Util.bytes(admin.getSalt());
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(
				admin,//principal 用户身份
				admin.getPassword(),//hashedCredentials已加密的凭证
				credentialsSalt,//credentialsSalt 密码加密时使用的盐
				getName());//realmName 当前方法所在对象的名字
		//5.返回封装结果(传递给认证管理器)
		return info;
	}

	/**
	 * 在此方法中完成授权信息的获取以及封装
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//获取主体对象用户信息
	Admin admin = (Admin) principals.getPrimaryPrincipal();
	//通过用户信息查询角色与中间表的角色id
	List<Integer> roleIds = adminRolesMapper.findRoleIdsByUserId(admin.getId());
	  if(roleIds==null&&roleIds.size()==0) {
		  throw new ServiceException("该用户没有分配角色");
	  }
		Integer[] roleIdsArray = {};
		//menuIds可能重复
		List<Integer> menuIds = roleMenusMapper.findMenuIdsByRoleId(roleIds.toArray(roleIdsArray));
		if(menuIds == null || menuIds.size() == 0) {
			throw new ServiceException("当前用户角色未分配菜单");
		}
	  
	  //通过角色id查询中间表
	  Integer[] RoleMenusIds= {};
	  List<String> findPermissions = menusMaper.findPermissions(roleIds.toArray(RoleMenusIds));
	 if(findPermissions==null&& findPermissions.size()==0) {
		 throw new ServiceException("该用户没有分配权限");
	 }
	 //权限信息去从
	 Set<String> hash=new HashSet<>();
	 for (String findPermission : findPermissions) {
		 if(!StringUtils.isEmpty(findPermission)) {
			 hash.add(findPermission);
		 }
		
	}
	 //对权限信息进行封装
	 
SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
	info.setStringPermissions(hash);
	
	return info;
	
	}


}
