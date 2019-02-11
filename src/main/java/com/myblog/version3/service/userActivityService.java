package com.myblog.version3.service;

import com.myblog.version3.entity.UserActivity;

import java.util.List;

public interface userActivityService {

    List<UserActivity> getByUid(String Uid);
}
