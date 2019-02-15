package com.myblog.version3.controller.Public;

import com.myblog.version3.entity.Article;
import com.myblog.version3.entity.User;
import com.myblog.version3.mapper.articleMapper;
import com.myblog.version3.mapper.messageMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Controller
public class PublicController {

    @Autowired
    messageMapper messageMapper;

    @Autowired
    articleMapper articleMapper;

    @GetMapping("/")
    @ApiIgnore
    public String HomePage(ModelMap modelMap){
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        if(session.getAttribute("isLogIn") !=null){
            modelMap.addAttribute("isLogIn",session.getAttribute("isLogIn"));
            User user = (User)session.getAttribute("User");
            modelMap.addAttribute("message" ,messageMapper.getByUid(user.getID()));
        }else {
            modelMap.addAttribute("isLogIn",false);
        }
        List<Article> articles = articleMapper.getAll();
        for(Article article : articles){
            article.setContent(article.getURL());
        }
        modelMap.addAttribute("articles" ,articles);
        return "public/index";
    }

    @RequestMapping("/Article/{Aid}")
    public String Article(@PathVariable(value = "Aid") String Aid, ModelMap modelMap){
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        if(session.getAttribute("isLogIn") !=null){
            modelMap.addAttribute("isLogIn",session.getAttribute("isLogIn"));
            User user = (User)session.getAttribute("User");
            modelMap.addAttribute("message" ,messageMapper.getByUid(user.getID()));
        }else {
            modelMap.addAttribute("isLogIn",false);
        }
        Article article = articleMapper.getByID(Aid);
        article.setContent(article.getURL());
        modelMap.addAttribute("article" ,article);
        modelMap.addAttribute("author" ,messageMapper.getByUid(article.getUid()));
        return "public/Article";
    }
}
