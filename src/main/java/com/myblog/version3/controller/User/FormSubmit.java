package com.myblog.version3.controller.User;

import com.google.gson.JsonObject;
import com.myblog.version3.Tools.Random;
import com.myblog.version3.entity.Category;
import com.myblog.version3.entity.Form.Article;
import com.myblog.version3.entity.Form.Comment;
import com.myblog.version3.entity.Form.Reply;
import com.myblog.version3.mapper.articleMapper;
import com.myblog.version3.mapper.categoryMapper;
import com.myblog.version3.mapper.commentMapper;
import com.myblog.version3.mapper.replyMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.annotations.Param;
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

    @RequestMapping(value = "/createArticle", method = {RequestMethod.GET, RequestMethod.POST})
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
                return json.toString();
            } else {
                json.addProperty("status", 0);
                json.addProperty("message", "添加文章失败");
                return json.toString();
            }
        }
    }

    @RequestMapping(value = "/updateArticle", method = {RequestMethod.GET, RequestMethod.POST})
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
            articleMapper.update(article1);
            json.addProperty("status", 1);
            json.addProperty("message", "更新文章成功");
            json.addProperty("URL", "/user/Article/" + article.getUid() + "/editArticle/" + article.getAid());
            return json.toString();
        }
    }


    @RequestMapping(value = "/addCategory", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiImplicitParams({
            @ApiImplicitParam(value = "分类名称", name = "category", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(value = "用户ID", name = "Uid", paramType = "query", dataType = "String", required = true)
    })
    public String addCategory(@NotBlank(message = "分类名不能为空") @Param(value = "category") String category, @Param(value = "Uid") String Uid) {
        Category newOne = new Category();
        newOne.setID(Random.getUUID().substring(0, 8));
        newOne.setName(category);
        newOne.setUid(Uid);
        try {
            categoryMapper.Insert(newOne);
            return "添加分类成功";
        } catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
            return "已存在相同的分类名";
        }
    }

    @RequestMapping(value = "/addComment" ,method = {RequestMethod.GET ,RequestMethod.POST})
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
                return json.toString();
            }else {
                json.addProperty("status", 0);
                json.addProperty("message", "添加评论失败，请稍后再试");
                return json.toString();
            }
        }
    }

    @RequestMapping(value = "/deleteComment" ,method = {RequestMethod.GET ,RequestMethod.POST})
    public Boolean deleteComment(@Param(value = "Cid") String Cid){
        return commentMapper.delete(Cid);
    }

    @RequestMapping(value = "/addReply" ,method = {RequestMethod.GET ,RequestMethod.POST})
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
                return json.toString();
            }else {
                json.addProperty("status", 0);
                json.addProperty("message", "回复失败，请稍后再试");
                return json.toString();
            }
        }
    }

    @RequestMapping(value = "/deleteReply" ,method = {RequestMethod.GET ,RequestMethod.POST})
    public Boolean deleteReply(@Param(value = "Rid") String Rid){
        return replyMapper.delete(Rid);
    }
}
