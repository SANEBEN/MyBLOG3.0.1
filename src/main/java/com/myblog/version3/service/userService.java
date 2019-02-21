package com.myblog.version3.service;

import com.myblog.version3.entity.User;

public interface userService {

    User getUserByID(String ID);

    User getByPhone(String phone);

    boolean addUser(User user);

    int DuplicateChecking(String phone);
}
