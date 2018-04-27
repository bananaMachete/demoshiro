package com.example.demo.config;


import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.LinkedHashMap;


/**
 * Apache Shiro 核心通过 Filter 来实现，就好像SpringMvc 通过DispachServlet 来主控制一样。
 * 既然是使用 Filter 一般也就能猜到，是通过URL规则来进行过滤和权限校验，所以我们需要定义一系列关于URL的规则和访问权限。
 */
@Configuration
public class ShiroConfiguration {
    @Bean(name="shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager manager) {
        System.err.println("--------------shiroFilter 正在工作 ----------------");
        ShiroFilterFactoryBean bean=new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager 
        bean.setSecurityManager( manager);
        //配置登录的url和登录成功的url
        bean.setLoginUrl("/login");
        bean.setSuccessUrl("/home");
        //配置访问权限,主要配置不被限制的资源
        LinkedHashMap<String, String> filterChainDefinitionMap=new LinkedHashMap<>();
        filterChainDefinitionMap.put("/jsp/login.jsp*", "anon");
        filterChainDefinitionMap.put("/loginUser", "anon");
        filterChainDefinitionMap.put("/logout*","anon");
        //需要提交数据时访问,开放给匿名可以访问.2018年4月25日10:40:21
        //authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问  
        filterChainDefinitionMap.put("/logon*","anon");
        filterChainDefinitionMap.put("/jsp/error.jsp*","anon");
        filterChainDefinitionMap.put("/jsp/index.jsp*","authc");
        filterChainDefinitionMap.put("/*", "authc");
        filterChainDefinitionMap.put("/**", "authc");
        filterChainDefinitionMap.put("/*.*", "authc");
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return bean;
    }

    /**
     *
     * SecurityManager：管理所有Subject，SecurityManager 是 Shiro 架构的核心，配合内部安全组件共同组成安全伞。
     * @param authRealm
     * @return
     */
    @Bean(name="securityManager")
    public SecurityManager securityManager(@Qualifier("authRealm") AuthRealm authRealm) {
        System.err.println("--------------shiro已经加载----------------");
        DefaultWebSecurityManager manager=new DefaultWebSecurityManager();
        manager.setRealm(authRealm);
        return manager;
    }

    //配置自定义的权限登录器
    @Bean(name="authRealm")
    public AuthRealm authRealm(@Qualifier("credentialsMatcher") CredentialsMatcher matcher) {
        AuthRealm authRealm=new AuthRealm();
        authRealm.setCredentialsMatcher(matcher);
        return authRealm;
    }
    //配置自定义的密码比较器,MD5等
    @Bean(name="credentialsMatcher")
    public CredentialsMatcher credentialsMatcher() {
        return new CredentialsMatcher();
    }
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator creator=new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager manager) {
        AuthorizationAttributeSourceAdvisor advisor=new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(manager);
        return advisor;
    }

    /**
     * 配置shiro标签
     * @return
     */
    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }

}
