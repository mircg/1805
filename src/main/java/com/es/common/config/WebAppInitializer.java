package com.es.common.config;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * web容器加载时启动此类
 * 相当于web.xml
 * 
 * 先后顺序：
 * 1.onStartup
 * 2.getRootConfigClasses
 * 3.registerShiroFilter
 * 4.getServletConfigClasses
 * 5.getServletMappings
 * 
 * @author Administrator
 *
 */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		// TODO Auto-generated method stub
	//super.onStartup(servletContext);
	registerContextLoaderListener(servletContext);
		//onStartup方法未注册filter,需注释掉重写
		//注册shiro过滤器
	registerShiroFilter(servletContext);
	registerDispatcherServlet(servletContext);
	}
	private void registerShiroFilter(ServletContext servletContext) {
		//注册filter对象
		FilterRegistration.Dynamic dy = servletContext.addFilter("filterProxy", DelegatingFilterProxy.class);
		dy.setInitParameter("targetBeanName", "shiroFilterFactoryBean");
		dy.addMappingForUrlPatterns(
				null,   //dispatcherTypes 
				false,  //isMatchAfter
				"/*");  //urlPatterns
	}
	/**
	 * 负责加载service、dao
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {AppRootConfig.class,AppShiroConfig.class};
	}
	/**
	 * 负责加载spring MVC组件
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {AppServletConfig.class};
	}
	/**
	 * 配置映射路径
	 */
	@Override
	protected String[] getServletMappings() {
		return new String[] {"*.do"};
	}

}
