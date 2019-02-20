package com.myblog.version3.controller.Public;

import com.myblog.version3.entity.Article;
import com.myblog.version3.entity.User;
import com.myblog.version3.entity.UserActivity;
import com.myblog.version3.mapper.*;
import io.swagger.annotations.ApiImplicitParam;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Controller
public class PublicController {

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

    @RequestMapping(value = "/personalCenter/{Uid}" ,method = RequestMethod.GET)
    @ApiImplicitParam(value = "用户的ID" ,name = "Uid" ,dataType = "String" ,paramType = "path" ,required = true)
    public String PersonalCenter(@PathVariable String Uid , ModelMap modelMap){
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        if(session.getAttribute("isLogIn") !=null){
            modelMap.addAttribute("isLogIn",session.getAttribute("isLogIn"));
            User user = (User)session.getAttribute("User");
            modelMap.addAttribute("user" ,messageMapper.getByUid(user.getID()));
        }else {
            modelMap.addAttribute("isLogIn",false);
        }
        modelMap.addAttribute("message",messageMapper.getByUid(Uid));
        modelMap.addAttribute("articles" ,articleMapper.getByUid(Uid));
        List<UserActivity> activities = userActivityMapper.getByUid(Uid);
        for(UserActivity activity : activities){
            if(activity.getAction().equals("createArticle")){
                activity.setArticle(articleMapper.getByID(activity.getObject_id()));
            }else if(activity.getAction().equals("updateArticle")){
                activity.setArticle(articleMapper.getByID(activity.getObject_id()));
            }else if(activity.getAction().equals("addCategory")){
                activity.setCategory(categoryMapper.getByID(activity.getOperation_object_id()));
            }else if(activity.getAction().equals("addComment")){
                activity.setComment(commentMapper.getByID(activity.getOperation_object_id()));
            }else if(activity.getAction().equals("addReply")){
                activity.setReply(replyMapper.getByID(activity.getOperation_object_id()));
            }
        }
        modelMap.addAttribute("activities" ,activities);
        return "public/personalCenter";
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
        modelMap.addAttribute("comments" ,commentMapper.getByAid(Aid));
        return "public/Article";
    }
}
