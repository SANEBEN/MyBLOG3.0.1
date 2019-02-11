package com.myblog.version3.controller.User;

import com.myblog.version3.entity.User;
import com.myblog.version3.mapper.articleMapper;
import com.myblog.version3.mapper.messageMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/user")
@Api(tags = "用户页面控制")
public class UserPageController {

    @Autowired
    messageMapper messageMapper;

    @Autowired
    articleMapper articleMapper;

    @RequestMapping(value = "/personalCenter/{Uid}" ,method = RequestMethod.GET)
    @ApiImplicitParam(value = "用户的ID" ,name = "Uid" ,dataType = "String" ,paramType = "path" ,required = true)
    public String PersonalCenter(@PathVariable String Uid , ModelMap modelMap){
        modelMap.addAttribute("message",messageMapper.getByUid(Uid));
        modelMap.addAttribute("articles" ,articleMapper.getByUid(Uid));
        return "authc/User/personalCenter";
    }

    @RequestMapping(value = "/Article/{Uid}" ,method = RequestMethod.GET)
    @ApiImplicitParam(value = "用户的ID" ,name = "Uid" ,dataType = "String" ,paramType = "path" ,required = true)
    public String ArticlesManagement(@PathVariable String Uid ,ModelMap modelMap){
        modelMap.addAttribute("message" ,messageMapper.getByUid(Uid));
        modelMap.addAttribute("articles" ,articleMapper.getByUid(Uid));
        return "authc/User/ArticlesManagement";
    }

    @RequestMapping(value = "/Article/{Uid}/editArticle/{Aid}" ,method = RequestMethod.GET)
    @ApiImplicitParam(value = "文章的ID" ,name = "Aid" ,paramType = "path" ,dataType = "String" ,required = true)
    public String editArticle(@PathVariable(value = "Uid") String Uid ,@PathVariable(value = "Aid") String Aid, ModelMap modelMap){
        modelMap.addAttribute("message" ,messageMapper.getByUid(Uid));
        modelMap.addAttribute("article" ,articleMapper.getByID(Aid));
        modelMap.addAttribute("articles" ,articleMapper.getByUid(Uid));
        return "authc/User/editArticle";
    }
}