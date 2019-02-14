package com.myblog.version3.controller.Article;

import com.myblog.version3.entity.Article;
import com.myblog.version3.mapper.articleMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "获得文章表里的信息")
public class ArticleTableController {

    @Autowired
    articleMapper articleMapper;

    @RequestMapping(value = "/message/getArticleByID" ,method = {RequestMethod.GET ,RequestMethod.POST})
    @ApiImplicitParam(value = "通过文章ID获得文章信息" ,name = "ID" ,dataType = "String" ,paramType = "query" ,required = true)
    public Article getByID(String ID){
        return articleMapper.getByID(ID);
    }

    @RequestMapping(value = "/message/getArticleByUid" ,method = {RequestMethod.GET ,RequestMethod.POST})
    @ApiImplicitParam(value = "通过用户ID获得文章信息" ,name = "Uid" ,dataType = "String" ,paramType = "query" ,required = true)
    public List<Article> getByUid(String Uid){
        return articleMapper.getByUid(Uid);
    }

    @RequestMapping(value = "/message/getAllArticles" ,method = {RequestMethod.GET ,RequestMethod.POST})
    @ApiOperation(value = "获得所有的文章" ,notes = "获得所有的文章，无参数")
    public List<Article> getAll(){return articleMapper.getAll();}
}
