package com.myblog.version3.controller.Management;

import com.myblog.version3.Tools.Redis;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "redis管理类")
public class redisManagement {
    @Autowired
    Redis redis;

}
