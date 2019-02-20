package com.myblog.version3.controller.User;

import com.myblog.version3.entity.Comment;
import com.myblog.version3.entity.Reply;
import com.myblog.version3.entity.User;
import com.myblog.version3.entity.UserActivity;
import com.myblog.version3.mapper.commentMapper;
import com.myblog.version3.mapper.replyMapper;
import com.myblog.version3.mapper.userActivityMapper;
import com.myblog.version3.mapper.userMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@Api(tags = "获得数据表里的信息")
public class TableController {

    @Autowired
    userMapper userMapper;

    @Autowired
    userActivityMapper userActivityMapper;

    @Autowired
    replyMapper replyMapper;

    @Autowired
    commentMapper commentMapper;

    @RequestMapping(value = "/message/getUserByID" ,method = {RequestMethod.GET ,RequestMethod.POST})
    @ApiImplicitParam(value = "通过ID获得用户表的信息" ,name = "ID" ,dataType = "String" ,paramType = "query" ,required = true)
    public User getByID(@Param(value = "ID") String ID){
        return userMapper.getByID(ID);
    }

    @RequestMapping(value = "/message/getActivityByUid" ,method = {RequestMethod.GET ,RequestMethod.POST})
    public List<UserActivity> getByUid(@Param(value = "Uid")String Uid){
        return userActivityMapper.getByUid(Uid);
    }

    @RequestMapping(value = "/message/getReplyByID" ,method = {RequestMethod.GET ,RequestMethod.POST})
    public Reply getReplyByID(@Param(value = "Rid")String Rid){
        return replyMapper.getByID(Rid);
    }

    @RequestMapping(value = "/message/getCommentByID" ,method = {RequestMethod.GET ,RequestMethod.POST})
    public Comment getCommentByID(@Param(value = "Cid")String Cid){
        return commentMapper.getByID(Cid);
    }


    @RequestMapping(value = "/message/getCommentByAid" ,method = {RequestMethod.GET ,RequestMethod.POST})
    public List<Comment> getCommentByAid(@Param(value = "Aid")String Aid){
        return commentMapper.getByAid(Aid);
    }
}
