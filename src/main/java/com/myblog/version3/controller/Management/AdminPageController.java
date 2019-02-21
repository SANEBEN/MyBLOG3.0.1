package com.myblog.version3.controller.Management;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.myblog.version3.entity.Article;
import com.myblog.version3.entity.Message;
import com.myblog.version3.entity.User;
import com.myblog.version3.mapper.articleMapper;
import com.myblog.version3.mapper.messageMapper;
import io.swagger.annotations.Api;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/admin")
@Api(tags = "管理员界面控制")
public class AdminPageController {
    @Autowired
    messageMapper messageMapper;

    @Autowired
    articleMapper articleMapper;

    @RequestMapping("/adminAccountManagement")
    public String adminManagement(ModelMap modelMap,@Param(value = "pageNum")int pageNum){
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()) {
            if (subject.hasRole("superAdmin")) {
                Session session = subject.getSession();
                User user = (User)session.getAttribute("User");
                modelMap.addAttribute("message" ,messageMapper.getByUid(user.getID()));
                PageHelper.startPage(pageNum,6);
                List<Message> users = messageMapper.getAllAdmin();
                PageInfo<Message> pageInfo = new PageInfo<>(users);
                modelMap.addAttribute("admins",pageInfo);
                return "authc/Admin/adminAccountManagement";
            }else {
                return "redirect:/";
            }
        }else {
            return "redirect:/login";
        }

    }

    @GetMapping("/articleManagement")
    public String articleManagement(ModelMap modelMap,@Param(value = "pageNum")int pageNum){
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()){
            if(subject.hasRole("admin")||subject.hasRole("superAdmin")){
                Session session = subject.getSession();
                User user = (User)session.getAttribute("User");
                modelMap.addAttribute("message" ,messageMapper.getByUid(user.getID()));
                PageHelper.startPage(pageNum,6);
                List<Article> articles = articleMapper.getAll();
                PageInfo<Article> pageInfo = new PageInfo<>(articles);
                modelMap.addAttribute("articles", pageInfo);
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
    public String userAccountManagement(ModelMap modelMap,@Param(value = "pageNum")int pageNum){
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()){
            if(subject.hasRole("admin")||subject.hasRole("superAdmin")){
                Session session = subject.getSession();
                User user = (User)session.getAttribute("User");
                modelMap.addAttribute("message" ,messageMapper.getByUid(user.getID()));
                PageHelper.startPage(pageNum,6);
                List<Message> users = messageMapper.getAllUser();
                PageInfo<Message> pageInfo = new PageInfo<>(users);
                modelMap.addAttribute("users",pageInfo);
                return "authc/Admin/UserAccountManagement";
            }else {
                return "redirect:/";
            }
        }else {
            return "redirect:/login";
        }
    }
}
