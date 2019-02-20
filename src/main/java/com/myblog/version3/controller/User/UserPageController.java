package com.myblog.version3.controller.User;

import com.myblog.version3.entity.Article;
import com.myblog.version3.mapper.articleMapper;
import com.myblog.version3.mapper.categoryMapper;
import com.myblog.version3.mapper.messageMapper;
import com.myblog.version3.mapper.userActivityMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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

    @Autowired
    categoryMapper categoryMapper;

    @RequestMapping(value = "/Article/{Uid}" ,method = RequestMethod.GET)
    @ApiImplicitParam(value = "用户的ID" ,name = "Uid" ,dataType = "String" ,paramType = "path" ,required = true)
    public String ArticlesManagement(@PathVariable String Uid ,ModelMap modelMap){
        modelMap.addAttribute("message" ,messageMapper.getByUid(Uid));
        modelMap.addAttribute("allArticle" ,articleMapper.getByUid(Uid));
        modelMap.addAttribute("categories",categoryMapper.getByUid(Uid));
        return "authc/User/ArticlesManagement";
    }

    @RequestMapping(value = "/Article/{Uid}/editArticle/{Aid}" ,method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(value = "用户的ID" ,name = "Uid" ,dataType = "String" ,paramType = "path" ,required = true),
            @ApiImplicitParam(value = "文章的ID" ,name = "Aid" ,dataType = "String" ,paramType = "path")
    })
    public String editArticle(@PathVariable(value = "Uid") String Uid ,@PathVariable(value = "Aid") String Aid, ModelMap modelMap){
        modelMap.addAttribute("message" ,messageMapper.getByUid(Uid));
        modelMap.addAttribute("categories",categoryMapper.getByUid(Uid));
        if(!Aid.equals("new")){
            Article article = articleMapper.getByID(Aid);
            article.setContent(article.getURL());
            modelMap.addAttribute("article" ,article);
        }
        return "authc/User/editArticle";
    }
}
