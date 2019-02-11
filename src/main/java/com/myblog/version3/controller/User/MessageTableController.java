package com.myblog.version3.controller.User;

import com.myblog.version3.entity.Message;
import com.myblog.version3.mapper.messageMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "获得用户信息表的信息")
public class MessageTableController {

    @Autowired
    messageMapper messageMapper;

    @RequestMapping(value = "/message/getMessageByUid" ,method = {RequestMethod.GET ,RequestMethod.POST})
    @ApiImplicitParam(value = "通过用户ID获得用户对应的信息表的信息" ,name = "Uid" ,dataType = "String" ,paramType = "query" ,required = true)
    public Message getByUid(@Param(value = "Uid") String Uid){
        return messageMapper.getByUid(Uid);
    }
}
