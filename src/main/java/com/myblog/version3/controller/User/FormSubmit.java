package com.myblog.version3.controller.User;

import com.google.gson.JsonObject;
import com.myblog.version3.Tools.Random;
import com.myblog.version3.Tools.Redis;
import com.myblog.version3.entity.Category;
import com.myblog.version3.entity.Form.*;
import com.myblog.version3.entity.Message;
import com.myblog.version3.entity.User;
import com.myblog.version3.entity.UserActivity;
import com.myblog.version3.mapper.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.*;
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

    @Autowired
    userMapper userMapper;

    @Autowired
    messageMapper messageMapper;

    @Autowired
    Redis redis;

    @RequestMapping(value = "/createArticle", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "新建文章", notes = "用于添加新的文章，登录后可访问该接口")
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
            String fileName = Random.forArticle(date);
            File file = new File("E:\\MyBLOG3.0.1\\src\\main\\resources\\static\\blogFile\\" + article.getUid() + "\\" + article.getCid() + "\\" + article1.getID(), article.getTitle() + "_" + fileName + ".html");
            file.getParentFile().mkdirs();
            if (file.createNewFile()) {
                logger.info("为用户" + article.getUid() + "创建了一个新的文件");
            }
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
            outputStream.write(data, 0, data.length);
            outputStream.flush();
            outputStream.close();
            article1.setURL("/blogFile/" + article.getUid() + "/" + article.getCid() + "/" + article1.getID()+"/"+ article.getTitle() + "_" + fileName + ".html");
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
    @ApiOperation(value = "更新文章", notes = "用于更新文章，登录后可访问该接口")
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
            if (!article.getCid().equals(origin.getCid())) {
                File newOne = new File("E:\\MyBLOG3.0.1\\src\\main\\resources\\static\\blogFile\\" + article.getUid() + "\\" + article.getCid() + "\\" + article.getAid());
                File oldOne = new File("E:\\MyBLOG3.0.1\\src\\main\\resources\\static\\blogFile\\" + article.getUid() + "\\" + origin.getCid() + "\\" + article.getAid());
                newOne.mkdirs();
                FileUtils.copyDirectory(oldOne, newOne);
                FileUtils.deleteDirectory(oldOne);
                logger.info("将用户" + article.getUid() + "存放文章的文件夹更改了存储位置");
            }
            String fileName = Random.forArticle(date);
            File file = new File("E:\\MyBLOG3.0.1\\src\\main\\resources\\static\\blogFile\\" + article.getUid() + "\\" + article.getCid() + "\\" + article.getAid(), article.getTitle() + "_" + fileName + ".html");
            if (file.createNewFile()) {
                logger.info("用户" + article.getUid() + "新建了一个文件");
            }
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
            outputStream.write(data, 0, data.length);
            outputStream.flush();
            outputStream.close();
            article1.setURL("/blogFile/" + article.getUid() + "/" + article.getCid() + "/" + article1.getID()+"/"+ article.getTitle() + "_" + fileName + ".html");
            if (articleMapper.update(article1)) {
                json.addProperty("status", 1);
                json.addProperty("message", "更新文章成功");
                json.addProperty("URL", "/user/Article/" + article.getUid() + "/editArticle/" + article.getAid());
                redis.updateArticleNumber(1);
            } else {
                json.addProperty("status", 0);
                json.addProperty("message", "更新文章失败，请稍后重试");
            }
            return json.toString();
        }
    }

    @RequestMapping(value = "/addCategory", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiImplicitParams({
            @ApiImplicitParam(value = "分类名称", name = "category", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(value = "用户ID", name = "Uid", paramType = "query", dataType = "String", required = true)
    })
    @ApiOperation(value = "添加分类", notes = "用于添加新的分类，登录后可访问该接口")
    public String addCategory(@NotBlank(message = "分类名不能为空") @Param(value = "category") String category, @Param(value = "Uid") String Uid) {
        Category newOne = new Category();
        newOne.setID(Random.getUUID().substring(0, 8));
        newOne.setName(category);
        newOne.setUid(Uid);
        try {
            if (categoryMapper.Insert(newOne)) {
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

    @RequestMapping(value = "/addComment", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "添加文章评论", notes = "用于添加文章评论，登录后可访问该接口")
    public String addComment(@Valid Comment comment, BindingResult bindingResult) {
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
            com.myblog.version3.entity.Comment newOne = new com.myblog.version3.entity.Comment();
            newOne.setContent(comment.getContent());
            newOne.setAid(comment.getAid());
            newOne.setUid(comment.getUid());
            newOne.setID(Random.getUUID().substring(0, 8));
            newOne.setTime(new Date());
            if (commentMapper.insert(newOne)) {
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
            } else {
                json.addProperty("status", 0);
                json.addProperty("message", "添加评论失败，请稍后再试");
                return json.toString();
            }
        }
    }

    @RequestMapping(value = "/addReply", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "添加回复", notes = "用于添加回复，登录后可访问该接口")
    public String addReply(@Valid Reply reply, BindingResult bindingResult) {
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
            com.myblog.version3.entity.Reply newOne = new com.myblog.version3.entity.Reply();
            newOne.setCid(reply.getCid());
            newOne.setReply_id(reply.getReply_id());
            newOne.setParent_reply_id(reply.getParent_reply_id());
            newOne.setContent(reply.getContent());
            newOne.setID(Random.getUUID().substring(0, 8));
            newOne.setTime(new Date());
            if (replyMapper.insert(newOne)) {
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
            } else {
                json.addProperty("status", 0);
                json.addProperty("message", "回复失败，请稍后再试");
                return json.toString();
            }
        }
    }

    @PostMapping("/updatePassword")
    public String changePassword(
            @NotBlank(message = "原密码不能为空") @Param(value = "originPassword") String originPassword,
            @Pattern(regexp = "[0-9a-zA-z_]{6,15}", message = "新密码不符合规范") @Param(value = "newPassword") String newPassword,
            @NotBlank(message = "请输入确认密码") @Param(value = "confirmPassword") String confirmPassword,
            @Param(value = "Uid") String Uid) {
        User user = userMapper.getByID(Uid);
        if (user.getPassword().equals(originPassword)) {
            if (newPassword.equals(confirmPassword)) {
                ByteSource credentialsSalt = ByteSource.Util.bytes(Uid);
                String password = new SimpleHash("MD5", newPassword
                        , credentialsSalt, 1024).toHex();
                if (userMapper.updatePassword(password, Uid)) {
                    return "修改密码成功";
                } else {
                    return "服务器错误，请稍后重试";
                }
            } else {
                return "两次输入的密码不相同,请重新输入";
            }
        } else {
            return "原密码输入错误";
        }
    }

    @PostMapping("/uploadHeadPortrait")
    public String updateHeadPortrait(HeadPortrait headPortrait) throws IOException {
        BufferedInputStream stream = new BufferedInputStream(headPortrait.getHeadPortrait().getInputStream());
        String path = "E:\\MyBLOG3.0.1\\src\\main\\resources\\static\\blogFile\\" + headPortrait.getUid();
        byte[] bs = new byte[1024];
        // 读取到的数据长度
        int len;
        // 输出的文件流保存到本地文件
        File Directory = new File(path);
        if (!Directory.exists()) {
            Directory.mkdirs();
        }
        String type = headPortrait.getHeadPortrait().getOriginalFilename().substring(headPortrait.getHeadPortrait().getOriginalFilename().indexOf(".") + 1);
        if(checkImageType(type)){
            File tempFile = new File(path, headPortrait.getHeadPortrait().getName() + "." + type);
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(tempFile));
            while ((len = stream.read(bs, 0, bs.length)) != -1) {
                outputStream.write(bs, 0, len);
            }
            outputStream.flush();
            outputStream.close();
            stream.close();
            String newPath = tempFile.getAbsolutePath().substring(49).replaceAll("\\\\", "/");
            if (messageMapper.updateHeadPortrait(
                    "/blogFile" + newPath,
                    headPortrait.getUid())) {
                return "上传成功";
            } else {
                return "上传失败，服务器错误";
            }
        }else {
            return "上传的图片格式不对";
        }

    }

    @PostMapping("/updateMessage")
    public String updateMessage(@Valid com.myblog.version3.entity.Form.Message message , BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> list = bindingResult.getFieldErrors();
            StringBuilder builder = new StringBuilder();
            for (FieldError error : list) {
                builder.append(error.getDefaultMessage() + "&");
            }
            return builder.toString();
        }else {
            Message newOne = new Message();
            newOne.setUserName(message.getUserName());
            newOne.setEmail(message.getEmail());
            newOne.setIntroduce(message.getIntroduce());
            newOne.setUid(message.getUid());
            if (messageMapper.update(newOne)) {
                return "更改信息成功";
            } else {
                return "更改失败，服务器出错。";
            }
        }

    }

    private boolean checkImageType(String type){
        String[] types = {"bmp" ,"gif" ,"jepg" ,"jpg" ,"png"};
        for(String i : types){
            if(type.equalsIgnoreCase(i)){
                return true;
            }
        }
        return false;
    }
}
