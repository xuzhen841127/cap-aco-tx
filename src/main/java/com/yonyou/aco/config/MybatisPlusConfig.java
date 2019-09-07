package com.yonyou.aco.config;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;

/**
 * @Description MybatisPlus配置类
 * @Author Sans
 * @CreateTime 2019/5/26 17:20
 */
@Configuration
public class MybatisPlusConfig implements WebMvcConfigurer {
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

	/**
	 * mybatis-plus SQL执行效率插件【生产环境可以关闭】
	 */
	@Bean
	public PerformanceMonitorInterceptor performanceInterceptor() {
		return new PerformanceMonitorInterceptor(true);
	}

	/**
	 * 分页插件
	 */
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		return new PaginationInterceptor();
	}
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true)); // true:允许输入空值，false:不能为空值
    }
}
