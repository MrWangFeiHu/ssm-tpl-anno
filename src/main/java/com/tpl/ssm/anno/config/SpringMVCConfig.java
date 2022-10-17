package com.tpl.ssm.anno.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/* 开启controller注解支持
 * 注意事项请参考：http://jinnianshilongnian.iteye.com/blog/1762632
 *
 * <context:component-scan base-package="com.tpl.ssm.cfg.controller"/>
 */
@ComponentScan(basePackages = {
        "com.tpl.ssm.anno.controller"
}, includeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, value = org.springframework.stereotype.Controller.class),
        @ComponentScan.Filter(type = FilterType.ANNOTATION, value = org.springframework.web.bind.annotation.ControllerAdvice.class)
}, useDefaultFilters = false)
@EnableWebMvc
@Configuration
// <import resource="spring-mvc-xxx.xml"/>
// @Import(value = {SpringMvcXxxConfig.class})
public class SpringMVCConfig implements WebMvcConfigurer {

    /* <!-- 配置SpringMVC视图解析器 -->
     * <bean id="viewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
     *     <property name="prefix" value="/pages/"/>
     *     <property name="suffix" value=".jsp"/>
     * </bean>
     */
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setViewClass(org.springframework.web.servlet.view.JstlView.class);
        resolver.setPrefix("/pages/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    /* <!-- 释放静态资源文件 -->
     * <mvc:resources mapping="/js/" location="/js/**"/>
     * <mvc:resources mapping="/css/" location="/css/**"/>
     * <mvc:resources mapping="/images/" location="/images/**"/>
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/").addResourceLocations("/js/**");
        registry.addResourceHandler("/css/").addResourceLocations("/css/**");
        registry.addResourceHandler("/images/").addResourceLocations("/images/**");
    }

    // 文件解析器
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        // maxUploadSizePerFile:单个文件大小限制（byte）
        // maxUploadSize：整个请求大小限制（byte）
        commonsMultipartResolver.setMaxUploadSizePerFile(10 * 1024 * 1024); // 10M
        commonsMultipartResolver.setMaxUploadSize(100 * 1024 * 1024); // 100M
        return commonsMultipartResolver;
    }

}
