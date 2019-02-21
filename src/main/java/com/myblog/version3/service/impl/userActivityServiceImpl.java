package com.myblog.version3.service.impl;

import com.myblog.version3.entity.UserActivity;
import com.myblog.version3.mapper.userActivityMapper;
import com.myblog.version3.service.userActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class userActivityServiceImpl implements userActivityService {

    @Autowired
    userActivityMapper mapper;

    @Override
    public List<UserActivity> getByUid(String Uid) {
        return mapper.getByUid(Uid);
    }
}
