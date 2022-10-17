package com.tpl.ssm.anno.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * Web应用启动入口
 * <p/>
 * <web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 * xmlns="http://xmlns.jcp.org/xml/ns/javaee"
 * xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee
 * http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd" id="WebApp_ID" version="3.1">
 * </web-app>
 */
public class WebInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // 父容器配置
        AnnotationConfigWebApplicationContext springCtx = new AnnotationConfigWebApplicationContext();
        springCtx.setDisplayName("ssm-tpl-anno"); // 部署应用的名称 <display-name>ssm-tpl-anno</display-name>

        /*
         * <!-- Spring配置文件开始  -->
         * <context-param>
         *     <param-name>contextConfigLocation</param-name>
         *     <param-value>
         *         classpath:spring-config.xml
         *     </param-value>
         * </context-param>
         * <listener>
         *     <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
         * </listener>
         * <!-- Spring配置文件结束 -->
         */
        springCtx.register(SpringConfig.class);
        // 通过监听器加载配置信息
        servletContext.addListener(new ContextLoaderListener(springCtx));

        /* 可以使用RequestContextHolder.currentRequestAttributes() 获取到请求的attr
         * <listener>
         *     <listener-class>org.springframework.web.context.request.RequestContextListener</listener-class>
         * </listener>
         */
        servletContext.addListener(org.springframework.web.context.request.RequestContextListener.class);

        // 子容器配置
        AnnotationConfigWebApplicationContext mvcCtx = new AnnotationConfigWebApplicationContext();
        /*
         * <!-- SpringMVC配置文件开始  -->
         * <servlet>
         *     <servlet-name>springMVC</servlet-name>
         *     <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
         *     <init-param>
         *         <param-name>contextConfigLocation</param-name>
         *         <param-value>classpath:spring-mvc.xml</param-value>
         *     </init-param>
         *     <load-on-startup>1</load-on-startup>
         *     <async-supported>true</async-supported>
         * </servlet>
         * <servlet-mapping>
         *     <servlet-name>springMVC</servlet-name>
         *     <url-pattern>/</url-pattern>
         * </servlet-mapping>
         * <!-- SpringMVC配置文件结束  -->
         */
        mvcCtx.register(SpringMVCConfig.class);
        ServletRegistration.Dynamic servlet = servletContext.addServlet(
                "DispatcherServlet",
                new DispatcherServlet(mvcCtx));
        servlet.setLoadOnStartup(1);
        servlet.setAsyncSupported(true);
        servlet.addMapping("/");

        /*
         * <!-- 设置servlet编码开始 -->
         * <filter>
         *     <filter-name>CharacterEncodingFilter</filter-name>
         *     <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
         *     <async-supported>true</async-supported>
         *     <init-param>
         *         <param-name>encoding</param-name>
         *         <param-value>UTF-8</param-value>
         *     </init-param>
         *     <init-param>
         *         <param-name>forceEncoding</param-name>
         *         <param-value>true</param-value>
         *     </init-param>
         * </filter>
         * <filter-mapping>
         *     <filter-name>CharacterEncodingFilter</filter-name>
         *     <url-pattern>/*</url-pattern>
         * </filter-mapping>
         * <!-- 设置servlet编码结束 -->
         */
        FilterRegistration.Dynamic encodingFilter = servletContext.addFilter(
                "CharacterEncodingFilter",
                new CharacterEncodingFilter("UTF-8", true));
        encodingFilter.setAsyncSupported(true);
        encodingFilter.addMappingForUrlPatterns(null, false, "/*");
    }
}
