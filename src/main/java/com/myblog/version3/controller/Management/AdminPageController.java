package com.myblog.version3.controller.Management;

import com.myblog.version3.entity.User;
import com.myblog.version3.mapper.messageMapper;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/admin")
@Api(tags = "管理员界面控制")
public class AdminPageController {
    @Autowired
    messageMapper messageMapper;

    @RequestMapping("/adminAccountManagement")
    public String adminManagement(ModelMap modelMap){
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()) {
            if (subject.hasRole("superAdmin")) {
                Session session = subject.getSession();
                User user = (User)session.getAttribute("User");
                modelMap.addAttribute("message" ,messageMapper.getByUid(user.getID()));
                return "authc/Admin/adminAccountManagement";
            }else {
                return "redirect:/";
            }
        }else {
            return "redirect:/login";
        }

    }

    @RequestMapping("/articleManagement")
    public String articleManagement(ModelMap modelMap){
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()){
            if(subject.hasRole("admin")||subject.hasRole("superAdmin")){
                Session session = subject.getSession();
                User user = (User)session.getAttribute("User");
                modelMap.addAttribute("message" ,messageMapper.getByUid(user.getID()));
                return "authc/Admin/ArticleManagement";
            }else {
                return "redirect:/";
            }
        }else {
            return "redirect:/login";
        }
    }

    @RequestMapping("/index")
    public String index(ModelMap modelMap){
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()){
            if(subject.hasRole("admin")||subject.hasRole("superAdmin")){
                Session session = subject.getSession();
                User user = (User)session.getAttribute("User");
                modelMap.addAttribute("message" ,messageMapper.getByUid(user.getID()));
                return "authc/Admin/index";
            }else {
                return "redirect:/";
            }

        }else {
            return "redirect:/login";
        }
    }

    @RequestMapping("/userAccountManagement")
    public String userAccountManagement(ModelMap modelMap){
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()){
            if(subject.hasRole("admin")||subject.hasRole("superAdmin")){
                Session session = subject.getSession();
                User user = (User)session.getAttribute("User");
                modelMap.addAttribute("message" ,messageMapper.getByUid(user.getID()));
                return "authc/Admin/UserAccountManagement";
            }else {
                return "redirect:/";
            }
        }else {
            return "redirect:/login";
        }
    }
}
