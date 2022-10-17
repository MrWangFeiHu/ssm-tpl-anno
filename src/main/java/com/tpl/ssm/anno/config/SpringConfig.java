package com.tpl.ssm.anno.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.MybatisConfiguration;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * Spring配置类
 *
 * <!-- 扫描service -->
 * <context:component-scan base-package="com.tpl.ssm.cfg.service"/>
 */
@ComponentScan(basePackages = {"com.tpl.ssm.anno.service"}, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, value = org.springframework.stereotype.Controller.class)
})
@EnableTransactionManagement
@Configuration
public class SpringConfig {

    private static final Logger logger = LoggerFactory.getLogger(SpringConfig.class);

    /*
     * <!-- 配置数据库连接池 -->
     * <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource" init-method="init" destroy-method="close">
     *     <!-- 四大连接参数 -->
     *     <property name="driverClassName" value="${jdbc.driverClassName}"/>
     *     <property name="url" value="${jdbc.url}"/>
     *     <property name="username" value="${jdbc.username}"/>
     *     <property name="password" value="${jdbc.password}"/>
     *     <!-- 连接池配置信息 -->
     *     <property name="initialSize" value="${jdbc.initialSize}"/>
     *     <property name="minIdle" value="${jdbc.minIdle}"/>
     *     <property name="maxActive" value="${jdbc.maxActive}"/>
     *     <property name="maxWait" value="${jdbc.maxWait}"/>
     *     <!-- 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒 -->
     *     <property name="timeBetweenEvictionRunsMillis" value="60000"/>
     *     <!-- 配置一个连接在池中最小生存的时间，单位是毫秒 -->
     *     <property name="minEvictableIdleTimeMillis" value="300000" />
     *     <!-- Druid用来测试连接是否可用的SQL语句,默认值每种数据库都不相同-->
     *     <property name="validationQuery" value="SELECT 'x'" />
     *     <!-- 指明连接是否被空闲连接回收器(如果有)进行检验.如果检测失败,则连接将被从池中去除. -->
     *     <property name="testWhileIdle" value="true" />
     *     <!-- 指明是否在从池中取出连接前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个 -->
     *     <property name="testOnBorrow" value="false" />
     *     <!-- 指明是否在归还到池中前进行检验 -->
     *     <property name="testOnReturn" value="false" />
     *     <!-- 配置监控统计拦截的filters -->
     *     <property name="filters" value="wall,stat" />
     * </bean>
     */
    @Bean(name = "dataSource")
    public DruidDataSource druidDataSource() {
        // 基本属性 url、user、password
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl("jdbc:mysql://localhost:3306/test");
        druidDataSource.setUsername("testuser");
        druidDataSource.setPassword("testuser");

        // 配置初始化大小、最小、最大
        druidDataSource.setInitialSize(10);
        druidDataSource.setMinIdle(10);
        druidDataSource.setMaxActive(50);

        // 配置获取连接等待超时的时间
        druidDataSource.setMaxWait(60000);
        // 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        druidDataSource.setTimeBetweenEvictionRunsMillis(60000);

        // 配置一个连接在池中最小生存的时间，单位是毫秒
        druidDataSource.setMinEvictableIdleTimeMillis(300000);

        druidDataSource.setValidationQuery("SELECT 1");
        druidDataSource.setTestWhileIdle(true);
        druidDataSource.setTestOnBorrow(false);
        druidDataSource.setTestOnReturn(false);

        // 打开PSCache，并且指定每个连接上PSCache的大小
        // 如果用Oracle，则把poolPreparedStatements配置为true，
        // mysql可以配置为false。
        druidDataSource.setPoolPreparedStatements(true);
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(20);

        // 配置监控统计拦截的filters
        try {
            druidDataSource.setFilters("wall,stat");
        } catch (SQLException e) {
            logger.error("配置监控统计拦截的filters error: ", e);
        }
        return druidDataSource;
    }

    /*
     * <!-- 配置sessionFactory -->
     * <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
     *     <!-- 注入数据源 -->
     *     <property name="dataSource" ref="dataSource"/>
     *     <!-- mapper文件位置 -->
     *     <property name="mapperLocations" value="classpath:mapper/*DAO.xml"/>
     *     <!-- mybatis核心配置文件位置 -->
     *     <property name="configLocation" value="classpath:config/mybatis-config.xml"/>
     * </bean>
     */
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactoryBean sqlSessionFactoryBean() {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setMapperLocations(resolveMapperLocations());
        sqlSessionFactory.setDataSource(druidDataSource());
        /*
         * <?xml version="1.0" encoding="UTF-8" ?>
         * <!DOCTYPE configuration
         *         PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
         *         "http://mybatis.org/dtd/mybatis-3-config.dtd">
         * <configuration>
         *
         *     <!-- 配置信息 -->
         *     <settings>
         *         <!-- 映射下划线到驼峰命名 -->
         *         <setting name="mapUnderscoreToCamelCase" value="true"/>
         *         <!-- 配置开启二级缓存 -->
         *         <setting name="cacheEnabled" value="true"/>
         *         <!-- 开启控制台打印SQL -->
         *         <setting name="logImpl" value="STDOUT_LOGGING"/>
         *     </settings>
         *
         *     <!-- 别名 -->
         *     <typeAliases>
         *         <package name="com.tpl.ssm.cfg.entity"/>
         *     </typeAliases>
         * </configuration>
         */
        MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
        mybatisConfiguration.setMapUnderscoreToCamelCase(true);
        mybatisConfiguration.setCacheEnabled(true);
        mybatisConfiguration.setLogImpl(StdOutImpl.class);
        mybatisConfiguration.getTypeAliasRegistry().registerAliases("com.tpl.ssm.anno.entity");

        sqlSessionFactory.setConfiguration(mybatisConfiguration);
        return sqlSessionFactory;
    }

    // https://wenku.baidu.com/view/1b9ade1640323968011ca300a6c30c225901f0c2.html
    public Resource[] resolveMapperLocations() {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        List<String> mapperLocations = new ArrayList<>();
        mapperLocations.add("classpath:mapper/*DAO.xml");
        List<Resource> resources = new ArrayList<>();
        for (String mapperLocation : mapperLocations) {
            Resource[] mappers = new Resource[0];
            try {
                mappers = resolver.getResources(mapperLocation);
            } catch (IOException e) {
                e.printStackTrace();
            }
            resources.addAll(Arrays.asList(mappers));
        }
        return resources.toArray(new Resource[0]);
    }

    /*
     * <!-- 配置扫描mapper生成代理对象 -->
     * <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
     *     <property name="basePackage" value="com.tpl.ssm.cfg.dao"/>
     *     <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>
     * </bean>
     */
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.tpl.ssm.anno.dao");
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        return mapperScannerConfigurer;
    }

    /* 事务管理器配置
     * <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
     *     <property name="dataSource" ref="dataSourceProxy"/>
     * </bean>
     */
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }

}
