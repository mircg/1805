package com.es.common.config;

import java.util.LinkedHashMap;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * 定义shiro框架的配置信息
 * @author Administrator
 *
 */
@Configuration
public class AppShiroConfig {
	/**
	 * rememberMe配置
	 * @return
	 */
	/*@Bean("rememberMeCookie")
	public SimpleCookie newSimpleCookie() {
		SimpleCookie cookie = new SimpleCookie("rememberMe");
		cookie.setHttpOnly(true);
		cookie.setMaxAge(7*24*60*60);
		return cookie;
	}*/
	/**
	 * 缓存管理器
	 * @return
	 */
	/*@Bean("rememberMeManager")
	public CookieRememberMeManager newCookieRememberMeManager(){
		CookieRememberMeManager manager = new CookieRememberMeManager();
		try {
			//cipherKey是加密rememberMe Cookie的密钥；默认AES算法
			byte[] cipherKey = Base64.decode("4AvVhmFLUs0KTA3Kprsdag==");
			manager.setCipherKey(cipherKey);
			manager.setCookie(newSimpleCookie());
		} catch (Base64DecodingException e) {
			e.printStackTrace();
		}
		return manager;
	}*/
	
	/**
	 * 配置shiro的SecurityManager
	 */
	@Bean("securityManager")
	public SecurityManager newSecurityManager(@Autowired AuthorizingRealm realm) {
		DefaultWebSecurityManager sManager = new DefaultWebSecurityManager();
		//通过realm访问数据库
		sManager.setRealm(realm);
		//注入Cookie管理器
		//sManager.setRememberMeManager(newCookieRememberMeManager());
		return sManager;
	}

	/**
	 * 配置ShiroFilterFactory工厂
	 * @param securityManager
	 * @return
	 */
	@Bean("shiroFilterFactoryBean")
	public ShiroFilterFactoryBean newShiroFilterFactoryBean(SecurityManager securityManager) {
		ShiroFilterFactoryBean bean=new ShiroFilterFactoryBean();
		bean.setSecurityManager(securityManager);
		//当此用户是一个非认证用户,需要先登陆进行认证
		bean.setLoginUrl("/login.do");
		
		LinkedHashMap<String,String> fcMap=
				new LinkedHashMap<>();
		fcMap.put("/bower_components/**","anon");//anon表示允许匿名访问
		fcMap.put("/dist/**", "anon");
		fcMap.put("/lib/**","anon");//anon表示允许匿名访问
		fcMap.put("/static/**", "anon");
		fcMap.put("/temp/**","anon");
		fcMap.put("/kim/**","anon");
		fcMap.put("/doLogin.do","anon");
		fcMap.put("/doLogout.do ","logout");
		// authc表示访问该地址用户必须身份验证通过
		//user表示访问该地址的用户是身份验证通过或RememberMe登录
		fcMap.put("/**", "anon");//authc
		bean.setFilterChainDefinitionMap(fcMap);
		return bean;
	}
	/***
	 * 配置shiro框架组件的生命周期管理对象
	 * @return
	 */
	@Bean("lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor newLifecycleBeanPostProcessor(){
		return new LifecycleBeanPostProcessor();
	}

	/**配置负责为Bean对象(需要授权访问的方法所在的对象)
	 * 创建代理对象的Bean组件*/
	@DependsOn(value="lifecycleBeanPostProcessor")
	@Bean
	public DefaultAdvisorAutoProxyCreator newDefaultAdvisorAutoProxyCreator(){
		return new DefaultAdvisorAutoProxyCreator();
	}
	/**
	 * 配置授权属性应用对象(在执行授权操作时需要用到此对象)
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor newAuthorizationAttributeSourceAdvisor(SecurityManager securityManager){
		AuthorizationAttributeSourceAdvisor bean = new AuthorizationAttributeSourceAdvisor();
		bean.setSecurityManager(securityManager);
		return bean;
	}

}
