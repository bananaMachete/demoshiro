package com.example.demo.config;

import com.example.demo.entity.Module;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.service.ModuleService;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AuthRealm extends AuthorizingRealm {
    /**
     * @author tim
     * Realms：用于进行权限信息的验证，我们自己实现。
     * Realm 本质上是一个特定的安全 DAO：它封装与数据源连接的细节，得到Shiro 所需的相关的数据。
     * 在配置 Shiro 的时候，你必须指定至少一个Realm 来实现认证（authentication）和/或授权（authorization）。
     * 授权认证
     */
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private ModuleService moduleService;
    /**
     *授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("权限配置-->AuthRealm.doGetAuthorizationInfo()");
        //获取session中的用户
        User user=(User) principals.fromRealm(this.getClass().getName()).iterator().next();
        List<String> permissions=new ArrayList<>();
        Set<Role> roles = user.getRoles();
        if(roles.size()>0) {
            for(Role role : roles) {
                Set<Module> modules = role.getModules();
                if(modules.size()>0) {
                    for(Module module : modules) {
                        permissions.add(module.getMname());
                    }
                }
            }
        }else{
            roles = roleService.getRolesByUserId(user.getId());
            System.out.println("角色配置-->AuthRealm.doGetAuthorizationInfo()--->"+roles.size());
            for (Role role : roles){
                Set<Module> modules = moduleService.findAllModule(role.getRid());
                if(modules.size()>0) {
                    for(Module module : modules) {
                        permissions.add(module.getMname());
                    }
                }
            }
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //将权限放入shiro中.
        info.addStringPermissions(permissions);
        return info;

    }

    /**
     * 认证.登录
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取用户输入的token
        UsernamePasswordToken utoken = (UsernamePasswordToken) token;
        String username = utoken.getUsername();
        User user = userService.getUserByUserName(username);
        //放入shiro.调用CredentialsMatcher检验密码
        return new SimpleAuthenticationInfo(user, user.getPassword(),this.getClass().getName());

    }
}
