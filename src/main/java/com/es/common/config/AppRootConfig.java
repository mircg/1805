package com.es.common.config;


import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageInterceptor;

/**
 * 整合数据源
 * 创建SqlSessionFactoryBean
 * @author Administrator
 *
 */
@Configuration
@ComponentScan(value="com.es",
excludeFilters= {@Filter(classes= {Controller.class,ControllerAdvice.class,Configuration.class})})
@PropertySource("classpath:configs.properties")
@MapperScan("com.es.**.mapper")
@EnableAspectJAutoProxy //启用Aop
@EnableTransactionManagement //启用事务管理  替代xml的<tx:annotation-driven />
public class AppRootConfig {
	
	@Bean(value="dataSource",initMethod="init",destroyMethod="close")
	@Lazy(false)
	@Scope("singleton")
	public DataSource newDataSource(
			@Value("${jdbcDriver}") String driver,
			@Value("${jdbcUrl}") String jdbcUrl,
			@Value("${jdbcUser}") String username,
			@Value("${jdbcPassword}") String password
			) {
		DruidDataSource ds = new DruidDataSource();
		ds.setDriverClassName(driver);
		ds.setUrl(jdbcUrl);
		ds.setUsername(username);
		ds.setPassword(password);
		return ds;
	}
	/**
	 * 整合SqlSessionFactoryBean对象
	 * 通过此对象创建SqlSessionFactory
	 * @param dataSource
	 * @return
	 * @throws IOException 
	 */
	
	@Bean("sqlSessionFactory")
	public SqlSessionFactoryBean newSqlSessionFactoryBean(@Autowired DataSource dataSource) throws IOException {
		SqlSessionFactoryBean fBean = new SqlSessionFactoryBean();
		fBean.setDataSource(dataSource);
		Resource[] mapperLocations = new PathMatchingResourcePatternResolver()
				.getResources("classpath*:sys/mapper/*Mapper.xml");
		fBean.setMapperLocations(mapperLocations);
		return fBean;
	}
	
	/**
	 * 解决${jdbcDriver}报ClassNotFoundException
	 * 让系统支持多个properties文件应用
	 * @return
	 */
	@Bean
	public PropertySourcesPlaceholderConfigurer getPropertySourcesPlaceholderConfigurer(){
		return new PropertySourcesPlaceholderConfigurer();
	}
	/**Spring中的声明事务控制*/
	/**
	 * 配置事务管理器
	 * @param dataSource
	 * @return
	 */
	@Bean("txManager")
	public DataSourceTransactionManager newDataSourceTransactionManager(DataSource dataSource) {
		DataSourceTransactionManager tx = new DataSourceTransactionManager();
		tx.setDataSource(dataSource);
		return tx;
	}
	/*@Bean

	public MapperScannerConfigurer getScanner(){

		MapperScannerConfigurer scanner=new MapperScannerConfigurer();

		scanner.setBasePackage("cn.tm.**.mapper");

		return scanner;

	}*/
	/**
	 * 文件上传
	 * @return
	 */

	
	@Bean("configuration")
	public FreeMarkerConfigurationFactoryBean newFreeMarkerConfigurationFactoryBean() {
			FreeMarkerConfigurationFactoryBean fFactoryBean = new FreeMarkerConfigurationFactoryBean();
			fFactoryBean.setTemplateLoaderPath("/static/freemaker");
			Properties freemarkerSettings = new Properties();
			freemarkerSettings.setProperty("default_encoding","UTF-8");
			fFactoryBean.setFreemarkerSettings(freemarkerSettings);
			return fFactoryBean;
	}
}
