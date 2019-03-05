package com.es.common.config;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

/**
 * 通过此配置文件整合MVC
 * @author Administrator
 *
 */
//@Configuration //可以省略(有@ComponentScan)
@ComponentScan(value="com.es",
includeFilters= {@Filter(value={Controller.class,ControllerAdvice.class})},
useDefaultFilters=false) //默认管理指定包下的所有类
@EnableWebMvc //启用MVC默认配置
public class AppServletConfig extends WebMvcConfigurerAdapter{ //spring-mvc.xml
	/**
	 * 配置视图解析器
	 */
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.jsp("/WEB-INF/pages/", ".html");
	}
	/**
	 * 整合fastjson库(提供了操作json的API)
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		//1.构建MessageConverter对象
		FastJsonHttpMessageConverter msConverter = new FastJsonHttpMessageConverter();
		//2.配置MessageConverter对象
		List<MediaType> list = new ArrayList<MediaType>();
		list.add(new MediaType("text","html",Charset.forName("utf-8")));
		list.add(new MediaType("application","json",Charset.forName("utf-8")));
		msConverter.setSupportedMediaTypes(list);
		//3.禁止fastjson循环依赖
		FastJsonConfig config = new FastJsonConfig();
		config.setSerializerFeatures(SerializerFeature.DisableCircularReferenceDetect);
		msConverter.setFastJsonConfig(config);
		//4.将MessageConverter对象添加到converters容器
		converters.add(msConverter);
	}

}
