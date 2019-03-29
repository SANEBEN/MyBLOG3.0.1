package com.myblog.version3.controller.Public;

import com.myblog.version3.Tools.Redis;
import com.myblog.version3.entity.Article;
import com.myblog.version3.entity.User;
import com.myblog.version3.entity.UserActivity;
import com.myblog.version3.mapper.*;
import io.swagger.annotations.ApiImplicitParam;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Controller
public class PublicController {
    private Logger logger = LoggerFactory.getLogger(PublicController.class);

    @Autowired
    messageMapper messageMapper;

    @Autowired
    articleMapper articleMapper;

    @Autowired
    commentMapper commentMapper;

    @Autowired
    userActivityMapper userActivityMapper;

    @Autowired
    categoryMapper categoryMapper;

    @Autowired
    replyMapper replyMapper;

    @Autowired
    private Redis redis;

    @GetMapping("/")
    @ApiIgnore
    public String HomePage(ModelMap modelMap){
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();
        if(user !=null){
            modelMap.addAttribute("isLogIn",true);
            modelMap.addAttribute("message" ,messageMapper.getByUid(user.getID()));
        }else {
            modelMap.addAttribute("isLogIn",false);
        }
        List<Article> articles = articleMapper.getAll();
        for(Article article : articles){
            article.setContent(article.getURL());
            article.setHits(redis.getArticleHits(article.getID()));
        }
        modelMap.addAttribute("articles" ,articles);
        modelMap.addAttribute("ArticleNumber" ,articleMapper.statistical());
        modelMap.addAttribute("UserNumber" ,messageMapper.statistical());
        modelMap.addAttribute("indexVisit",redis.getIndexVisit());
        redis.updateIndexVisit();
        return "public/index";
    }

    @RequestMapping(value = "/personalCenter/{Uid}" ,method = RequestMethod.GET)
    @ApiImplicitParam(value = "用户的ID" ,name = "Uid" ,dataType = "String" ,paramType = "path" ,required = true)
    public String PersonalCenter(@PathVariable String Uid , ModelMap modelMap){
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();
        if(user !=null){
            modelMap.addAttribute("isLogIn",true);
            modelMap.addAttribute("user" ,messageMapper.getByUid(user.getID()));
        }else {
            modelMap.addAttribute("isLogIn",false);
        }
        modelMap.addAttribute("message",messageMapper.getByUid(Uid));
        modelMap.addAttribute("articles" ,articleMapper.getByUid(Uid));
        return "public/personalCenter";
    }

    @RequestMapping("/Article/{Aid}")
    public String Article(@PathVariable(value = "Aid") String Aid, ModelMap modelMap){
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();
        if(user !=null){
            modelMap.addAttribute("isLogIn",true);
            modelMap.addAttribute("user" ,messageMapper.getByUid(user.getID()));
        }else {
            modelMap.addAttribute("isLogIn",false);
        }
        Article article = articleMapper.getByID(Aid);
        article.setContent(article.getURL());
        modelMap.addAttribute("article" ,article);
        modelMap.addAttribute("author" ,messageMapper.getByUid(article.getUid()));
        modelMap.addAttribute("comments" ,commentMapper.getByAid(Aid));
        modelMap.addAttribute("hits" ,redis.getArticleHits(Aid));
        redis.updateArticleHits(Aid);
        return "public/Article";
    }

    @RequestMapping("/permissionForbid")
    public String permissionForbid(ModelMap modelMap){
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();
        if(user !=null){
            modelMap.addAttribute("isLogIn",true);
            modelMap.addAttribute("user" ,messageMapper.getByUid(user.getID()));
        }else {
            modelMap.addAttribute("isLogIn",false);
        }
        modelMap.addAttribute("data" ,"你没有访问该界面的权限");
        return "public/permissionForbid";
    }
}
