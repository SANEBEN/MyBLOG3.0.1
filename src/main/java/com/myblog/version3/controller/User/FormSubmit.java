package com.myblog.version3.controller.User;

import com.google.gson.JsonObject;
import com.myblog.version3.Tools.Random;
import com.myblog.version3.entity.Category;
import com.myblog.version3.entity.Form.Article;
import com.myblog.version3.entity.Form.Comment;
import com.myblog.version3.entity.Form.Reply;
import com.myblog.version3.entity.User;
import com.myblog.version3.entity.UserActivity;
import com.myblog.version3.mapper.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/Form")
@Api(tags = "用户相关表单提交处理类")
public class FormSubmit {

    private Logger logger = LoggerFactory.getLogger(FormSubmit.class);

    @Autowired
    articleMapper articleMapper;

    @Autowired
    categoryMapper categoryMapper;

    @Autowired
    commentMapper commentMapper;

    @Autowired
    replyMapper replyMapper;

    @Autowired
    userActivityMapper userActivityMapper;

    @RequestMapping(value = "/createArticle", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "新建文章" ,notes = "用于添加新的文章，登录后可访问该接口")
    public String insert(@Valid Article article, BindingResult bindingResult) throws IOException {
        JsonObject json = new JsonObject();
        if (bindingResult.hasErrors()) {
            List<FieldError> list = bindingResult.getFieldErrors();
            StringBuilder builder = new StringBuilder();
            for (FieldError error : list) {
                builder.append(error.getDefaultMessage() + "&");
            }
            json.addProperty("status", 0);
            json.addProperty("message", builder.toString());
            return json.toString();
        } else {
            com.myblog.version3.entity.Article article1 = new com.myblog.version3.entity.Article();
            article1.setID(Random.getUUID().substring(0, 8));
            article1.setTitle(article.getTitle());
            Date date = new Date();
            article1.setCreatedTime(date);
            article1.setChangeTime(date);
            article1.setUid(article.getUid());
            article1.setCid(article.getCid());
            if (article.getIsPrivate() != null) {
                article1.setPrivate(true);
            } else {
                article1.setPrivate(false);
            }
            if (article.getAllowComment() != null) {
                article1.setAllowComment(true);
            } else {
                article1.setAllowComment(false);
            }
            String content = article.getContent();
            byte[] data = content.getBytes();
            File file = new File("E:\\MyBLOGFileFolder\\" + article.getUid() + "\\" + article.getCid() + "\\" + article1.getID(), article.getTitle() + "_" + Random.forArticle(date) + ".html");
            file.getParentFile().mkdirs();
            if (file.createNewFile()) {
                logger.info("为用户" + article.getUid() + "创建了一个新的文件");
            }
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
            outputStream.write(data, 0, data.length);
            outputStream.flush();
            outputStream.close();
            article1.setURL(file.getPath());
            if (articleMapper.insert(article1)) {
                json.addProperty("status", 1);
                json.addProperty("message", "添加文章成功");
                json.addProperty("URL", "/user/Article/" + article.getUid() + "/editArticle/" + article1.getID());
                UserActivity activity = new UserActivity();
                activity.setAction("createArticle");
                activity.setCreated_time(date);
                activity.setID(Random.getUUID().substring(0,8));
                activity.setUid(article.getUid());
                activity.setObject_id(article1.getID());
                userActivityMapper.Article(activity);
                return json.toString();
            } else {
                json.addProperty("status", 0);
                json.addProperty("message", "添加文章失败");
                return json.toString();
            }
        }
    }

    @RequestMapping(value = "/updateArticle", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "更新文章" ,notes = "用于更新文章，登录后可访问该接口")
    public String updateArticle(@Valid Article article, BindingResult bindingResult) throws IOException {
        JsonObject json = new JsonObject();
        if (bindingResult.hasErrors()) {
            List<FieldError> list = bindingResult.getFieldErrors();
            StringBuilder builder = new StringBuilder();
            for (FieldError error : list) {
                builder.append(error.getDefaultMessage() + "&");
            }
            json.addProperty("status", 0);
            json.addProperty("message", builder.toString());
            return json.toString();
        } else {
            com.myblog.version3.entity.Article article1 = new com.myblog.version3.entity.Article();
            article1.setCid(article.getCid());
            if (article.getIsPrivate() != null) {
                article1.setPrivate(true);
            } else {
                article1.setPrivate(false);
            }
            if (article.getAllowComment() != null) {
                article1.setAllowComment(true);
            } else {
                article1.setAllowComment(false);
            }
            Date date = new Date();
            article1.setChangeTime(date);
            article1.setID(article.getAid());
            article1.setTitle(article.getTitle());
            String content = article.getContent();
            byte[] data = content.getBytes();
            com.myblog.version3.entity.Article origin = articleMapper.getByID(article.getAid());
            if(!article.getCid().equals(origin.getCid())){
                File newOne = new File("E:\\MyBLOGFileFolder\\" + article.getUid() + "\\" + article.getCid() + "\\" + article.getAid());
                File oldOne = new File("E:\\MyBLOGFileFolder\\" + article.getUid() + "\\" + origin.getCid() + "\\" + article.getAid());
                newOne.mkdirs();
                FileUtils.copyDirectory(oldOne,newOne);
                FileUtils.deleteDirectory(oldOne);
                logger.info("将用户"+article.getUid()+"存放文章的文件夹更改了存储位置");
            }
            File file = new File("E:\\MyBLOGFileFolder\\" + article.getUid() + "\\" + article.getCid() + "\\" + article.getAid(), article.getTitle() + "_" + Random.forArticle(date) + ".html");
            if (file.createNewFile()) {
                logger.info("用户" + article.getUid() + "新建了一个文件");
            }
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
            outputStream.write(data, 0, data.length);
            outputStream.flush();
            outputStream.close();
            article1.setURL(file.getPath());
            if(articleMapper.update(article1)){
                json.addProperty("status", 1);
                json.addProperty("message", "更新文章成功");
                json.addProperty("URL", "/user/Article/" + article.getUid() + "/editArticle/" + article.getAid());
                UserActivity activity = new UserActivity();
                activity.setAction("updateArticle");
                activity.setCreated_time(new Date());
                activity.setID(Random.getUUID().substring(0,8));
                activity.setUid(article.getUid());
                activity.setObject_id(article1.getID());
                userActivityMapper.Article(activity);
            }else {
                json.addProperty("status", 0);
                json.addProperty("message", "更新文章失败，请稍后重试");
            }
            return json.toString();
        }
    }

    @RequestMapping(value = "/deleteArticle" ,method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "删除文章" ,notes = "只用管理员或者作者能访问这个接口，需验证权限")
    public String deleteArticle(@Param(value = "Aid") String Aid ) throws IOException{
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getSession().getAttribute("User");
        com.myblog.version3.entity.Article article = articleMapper.getByID(Aid);
        if(subject.hasRole("admin")||user.getID().equals(article.getUid())) {
            if(articleMapper.deleteByAid(Aid)) {
                FileUtils.deleteDirectory(new File(article.getURL()).getParentFile());
                UserActivity activity = new UserActivity();
                activity.setAction("deleteArticle");
                activity.setCreated_time(new Date());
                activity.setID(Random.getUUID().substring(0, 8));
                activity.setUid(user.getID());
                activity.setObject_id(article.getTitle());
                userActivityMapper.Article(activity);
                return "删除文章成功";
            }else {
                return "删除失败，请联系管理员了解详情";
            }
        }else {
            return "你没有删除该文章的权限";
        }
    }

    @RequestMapping(value = "/addCategory", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiImplicitParams({
            @ApiImplicitParam(value = "分类名称", name = "category", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(value = "用户ID", name = "Uid", paramType = "query", dataType = "String", required = true)
    })
    @ApiOperation(value = "添加分类" ,notes = "用于添加新的分类，登录后可访问该接口")
    public String addCategory(@NotBlank(message = "分类名不能为空") @Param(value = "category") String category, @Param(value = "Uid") String Uid) {
        Category newOne = new Category();
        newOne.setID(Random.getUUID().substring(0, 8));
        newOne.setName(category);
        newOne.setUid(Uid);
        try {
            if(categoryMapper.Insert(newOne)){
                UserActivity activity = new UserActivity();
                activity.setAction("addCategory");
                activity.setCreated_time(new Date());
                activity.setID(Random.getUUID().substring(0, 8));
                activity.setUid(Uid);
                activity.setOperation_object_id(newOne.getID());
                userActivityMapper.Other(activity);
            }
            return "添加分类成功";
        } catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
            return "已存在相同的分类名";
        }
    }

    @RequestMapping(value = "/deleteCategory", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "删除文章分类" ,notes = "只用管理员或者作者能访问这个接口，需验证权限")
    public String deleteCategory(@Param(value = "Cid") String Cid) throws IOException{
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getSession().getAttribute("User");
        Category category = categoryMapper.getByID(Cid);
        if(subject.hasRole("admin")||category.getUid().equals(user.getID())){
            if(categoryMapper.delete(Cid)) {
                for(com.myblog.version3.entity.Article article : category.getArticles()){
                    FileUtils.deleteDirectory(new File(article.getURL()).getParentFile());
                }
                UserActivity activity = new UserActivity();
                activity.setAction("addCategory");
                activity.setCreated_time(new Date());
                activity.setID(Random.getUUID().substring(0, 8));
                activity.setUid(user.getID());
                activity.setObject_id(category.getName());
                userActivityMapper.Other(activity);
                return "删除分类成功";
            }else {
                return "删除失败，请联系管理员了解详情";
            }

        }else {
            return "你没有权限删除该分类";
        }
    }

    @RequestMapping(value = "/addComment" ,method = {RequestMethod.GET ,RequestMethod.POST})
    @ApiOperation(value = "添加文章评论" ,notes = "用于添加文章评论，登录后可访问该接口")
    public String addComment(@Valid Comment comment ,BindingResult bindingResult){
        JsonObject json = new JsonObject();
        if(bindingResult.hasErrors()){
            List<FieldError> list = bindingResult.getFieldErrors();
            StringBuilder builder = new StringBuilder();
            for (FieldError error : list) {
                builder.append(error.getDefaultMessage() + "&");
            }
            json.addProperty("status", 0);
            json.addProperty("message", builder.toString());
            return json.toString();
        }else {
            com.myblog.version3.entity.Comment newOne = new com.myblog.version3.entity.Comment();
            newOne.setContent(comment.getContent());
            newOne.setAid(comment.getAid());
            newOne.setUid(comment.getUid());
            newOne.setID(Random.getUUID().substring(0,8));
            newOne.setTime(new Date());
            if(commentMapper.insert(newOne)){
                json.addProperty("status", 1);
                json.addProperty("message", "添加评论成功");
                UserActivity activity = new UserActivity();
                activity.setAction("addComment");
                activity.setCreated_time(new Date());
                activity.setID(Random.getUUID().substring(0, 8));
                activity.setUid(comment.getUid());
                activity.setOperation_object_id(newOne.getID());
                userActivityMapper.Other(activity);
                return json.toString();
            }else {
                json.addProperty("status", 0);
                json.addProperty("message", "添加评论失败，请稍后再试");
                return json.toString();
            }
        }
    }

    @RequestMapping(value = "/deleteComment" ,method = {RequestMethod.GET ,RequestMethod.POST})
    @ApiOperation(value = "删除评论" ,notes = "用于删除评论，只用文章作者和网站管理者有权调用该接口")
    public String deleteComment(@Param(value = "Cid") String Cid){
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getSession().getAttribute("User");
        com.myblog.version3.entity.Comment comment = commentMapper.getByID(Cid);
        if(subject.hasRole("admin")||user.getID().equals(comment.getUid())){
            if(commentMapper.delete(Cid)){
                return "删除评论成功";
            }else {
                return "删除失败，请稍后重试";
            }
        }else {
            return "你没有删除评论的权限";
        }
    }

    @RequestMapping(value = "/addReply" ,method = {RequestMethod.GET ,RequestMethod.POST})
    @ApiOperation(value = "添加回复" ,notes = "用于添加回复，登录后可访问该接口")
    public String addReply(@Valid Reply reply ,BindingResult bindingResult){
        JsonObject json = new JsonObject();
        if(bindingResult.hasErrors()){
            List<FieldError> list = bindingResult.getFieldErrors();
            StringBuilder builder = new StringBuilder();
            for (FieldError error : list) {
                builder.append(error.getDefaultMessage() + "&");
            }
            json.addProperty("status", 0);
            json.addProperty("message", builder.toString());
            return json.toString();
        }else {
            com.myblog.version3.entity.Reply newOne = new com.myblog.version3.entity.Reply();
            newOne.setCid(reply.getCid());
            newOne.setReply_id(reply.getReply_id());
            newOne.setParent_reply_id(reply.getParent_reply_id());
            newOne.setContent(reply.getContent());
            newOne.setID(Random.getUUID().substring(0,8));
            newOne.setTime(new Date());
            if(replyMapper.insert(newOne)){
                json.addProperty("status", 1);
                json.addProperty("message", "回复成功");
                UserActivity activity = new UserActivity();
                activity.setAction("addReply");
                activity.setCreated_time(new Date());
                activity.setID(Random.getUUID().substring(0, 8));
                activity.setUid(reply.getReply_id());
                activity.setOperation_object_id(newOne.getID());
                userActivityMapper.Other(activity);
                return json.toString();
            }else {
                json.addProperty("status", 0);
                json.addProperty("message", "回复失败，请稍后再试");
                return json.toString();
            }
        }
    }

    @RequestMapping(value = "/deleteReply" ,method = {RequestMethod.GET ,RequestMethod.POST})
    @ApiOperation(value = "删除回复" ,notes = "用于删除评论，只用文章作者和网站管理者有权调用该接口")
    public String deleteReply(@Param(value = "Rid") String Rid){
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getSession().getAttribute("User");
        com.myblog.version3.entity.Reply reply = replyMapper.getByID(Rid);
        if(subject.hasRole("admin")||user.getID().equals(reply.getReply_id())){
            if(replyMapper.delete(Rid)){
                return "删除回复成功";
            }else {
                return "删除失败，请稍后重试";
            }
        }else {
            return "你没有删除该回复的权限";
        }
    }
}
