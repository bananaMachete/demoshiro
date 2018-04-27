package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class IndexController {
    /**
     * @author xpf
     * @param mv
     * @return
     */
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/")
    public ModelAndView index(ModelAndView mv) {
        mv.setViewName("/login");
        mv.addObject("title","欢迎使用Thymeleaf!");
        return mv;
    }

    @RequestMapping(value = "/users")
    public ModelAndView finAllUsers(ModelAndView mv) {
        mv.setViewName("/list");
        List<User> userList = userService.findAllUsers();
        mv.addObject("userList",userList);
        return mv;
    }

    @RequestMapping(value = "/logon",method = RequestMethod.POST)
    public ModelAndView logon(String username,String password,HttpSession session) {
        System.out.println("logon,"+username+"||"+password);
        ModelAndView mv = new ModelAndView();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username,password);
        //当前用户，Subject 可以是一个人，但也可以是第三方服务、守护进程帐户、时钟守护任务或者其它–当前和软件交互的任何事件。
        Subject subject = SecurityUtils.getSubject();
        try {
            //完成登录
            subject.login(usernamePasswordToken);
            User user = (User) subject.getPrincipal();
            session.setAttribute("user", user);
            mv.setViewName("/index");
        } catch(Exception e) {
            mv.setViewName("/login");
        }
        mv.setViewName("/index");
        return mv;
    }

    /**
     * 转入到登录页面
     * @param mv
     * @return
     */
    @RequestMapping(value = "/login")
    public ModelAndView login(ModelAndView mv) {
        mv.setViewName("/login");
        mv.addObject("title","欢迎使用光临!");
        return mv;
    }
}
