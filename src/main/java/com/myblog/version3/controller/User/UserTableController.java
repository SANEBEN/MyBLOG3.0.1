package com.myblog.version3.controller.User;

import com.myblog.version3.entity.User;
import com.myblog.version3.mapper.userMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "获得用户表的信息")
public class UserTableController {

    @Autowired
    userMapper userMapper;

    @RequestMapping(value = "/message/getUserByID" ,method = {RequestMethod.GET ,RequestMethod.POST})
    @ApiImplicitParam(value = "通过ID获得用户表的信息" ,name = "ID" ,dataType = "String" ,paramType = "query" ,required = true)
    public User getByID(@Param(value = "ID") String ID){
        return userMapper.getByID(ID);
    }
}
