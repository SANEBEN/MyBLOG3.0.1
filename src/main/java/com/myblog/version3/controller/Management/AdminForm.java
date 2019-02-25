package com.myblog.version3.controller.Management;

import com.myblog.version3.Tools.Redis;
import com.myblog.version3.entity.Article;
import com.myblog.version3.entity.Category;
import com.myblog.version3.entity.Comment;
import com.myblog.version3.entity.User;
import com.myblog.version3.mapper.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/AdminForm")
@Api(tags = "管理员相关表单提交处理类")
public class AdminForm {

    private Logger logger = LoggerFactory.getLogger(AdminForm.class);

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


    @RequestMapping(value = "/deleteArticle", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "删除文章", notes = "只用管理员或者作者能访问这个接口，需验证权限")
    public String deleteArticle(@Param(value = "Aid") String Aid) throws IOException {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getSession().getAttribute("User");
        com.myblog.version3.entity.Article article = articleMapper.getByID(Aid);
        if (subject.hasRole("admin") || user.getID().equals(article.getUid())) {
            if (articleMapper.deleteByAid(Aid)) {
                logger.info("=====================删除文章测试=====================");
                FileUtils.deleteDirectory(new File(article.getURL()).getParentFile());
                List<Comment> comments = commentMapper.getByAid(Aid);
                for (com.myblog.version3.entity.Comment Cid : comments) {
                    deleteComment(Cid.getID());
                }
                redis.updateArticleNumber(-1);
                return "删除文章成功";
            } else {
                return "删除失败，请联系管理员了解详情";
            }
        } else {
            return "你没有删除该文章的权限";
        }
    }


    @RequestMapping(value = "/deleteCategory", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "删除文章分类", notes = "只用管理员或者作者能访问这个接口，需验证权限")
    public String deleteCategory(@Param(value = "Cid") String Cid) throws IOException {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getSession().getAttribute("User");
        Category category = categoryMapper.getByID(Cid);
        if (subject.hasRole("admin") || category.getUid().equals(user.getID())) {
            logger.info("=====================删除分类测试=====================");
            if (categoryMapper.delete(Cid)) {
                logger.info("分类名：" + category.getName());
                if (!category.getArticles().isEmpty()) {
                    for (com.myblog.version3.entity.Article article : category.getArticles()) {
                        deleteArticle(article.getID());
                        FileUtils.deleteDirectory(new File(article.getURL()).getParentFile());
                    }
                }
                return "删除分类成功";
            } else {
                return "删除失败，请联系管理员了解详情";
            }

        } else {
            return "你没有权限删除该分类";
        }
    }

    @RequestMapping(value = "/deleteComment", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "删除评论", notes = "用于删除评论，只用文章作者和网站管理者有权调用该接口")
    public String deleteComment(@Param(value = "Cid") String Cid) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getSession().getAttribute("User");
        com.myblog.version3.entity.Comment comment = commentMapper.getByID(Cid);
        if (subject.hasRole("superAdmin") || subject.hasRole("admin") || user.getID().equals(comment.getUid())) {
            if (commentMapper.delete(Cid)) {
                replyMapper.deleteByCid(Cid);
                return "删除评论成功";
            } else {
                return "删除失败，请稍后重试";
            }
        } else {
            return "你没有删除评论的权限";
        }
    }


    @RequestMapping(value = "/deleteReply", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "删除回复", notes = "用于删除评论，只用文章作者和网站管理者有权调用该接口")
    public String deleteReply(@Param(value = "Rid") String Rid) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getSession().getAttribute("User");
        com.myblog.version3.entity.Reply reply = replyMapper.getByID(Rid);
        if (subject.hasRole("admin") || user.getID().equals(reply.getReply_id())) {
            if (replyMapper.delete(Rid)) {
                return "删除回复成功";
            } else {
                return "删除失败，请稍后重试";
            }
        } else {
            return "你没有删除该回复的权限";
        }
    }

    @RequestMapping(value = "/commentStatus")
    @ApiOperation(value = "更改评论的状态", notes = "用于删除评论，只用文章作者和网站管理者有权调用该接口")
    public String articleAllowComment(@Param(value = "Aid") String Aid, @Param(value = "status") boolean status) {
        if (articleMapper.commentStatus(status, Aid)) {
            return "修改状态成功";
        } else {
            return "修改状态失败";
        }
    }

    @RequestMapping(value = "/deleteUser")
    @ApiOperation(value = "删除用户", notes = "用于删除评论，只用网站管理者有权调用该接口")
    public String deleteUser(@Param(value = "Uid") String Uid) throws IOException {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            User user = (User) subject.getSession().getAttribute("User");
            String Role = messageMapper.getByUid(user.getID()).getRole();
            if (Role.equals("admin") || Role.equals("admin,superAdmin")) {
                if (userMapper.delete(Uid)) {
                    messageMapper.delete(Uid);
                    List<Category> categories = categoryMapper.getByUid(Uid);
                    logger.info("=====================删除用户测试=====================");
                    for (Category category : categories) {
                        logger.info("用户的文章分类：" + category.getName() + "  " + category.getID());
                        deleteCategory(category.getID());
                    }
                    FileUtils.deleteDirectory(new File("E:\\MyBLOGFileFolder\\" + Uid));
                    redis.updateUserNumber(-1);
                    return "删除用户成功";
                } else {
                    return "删除失败，请稍后再试";
                }
            } else {
                return "你没有进行该操作的权限";
            }

        } else {
            return "请登录后重试";
        }

    }

    @RequestMapping(value = "/userStatus")
    @ApiOperation(value = "更改用户状态", notes = "用于删除评论，只用网站管理者有权调用该接口")
    public String userStatus(@Param(value = "Uid") String Uid, @Param(value = "status") int status) {
        if (messageMapper.userStatus(Uid, status)) {
            return "更改用户状态成功";
        } else {
            return "更改失败，请稍后再试";
        }
    }
}
