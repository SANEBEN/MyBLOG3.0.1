package com.myblog.version3.service.impl;

import com.myblog.version3.entity.User;
import com.myblog.version3.mapper.userMapper;
import com.myblog.version3.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements userService {
    @Autowired
    private userMapper user;

    @Override
    public User getByPhone(String phone) {
        return user.getByPhone(phone);
    }

    @Override
    public User getUserByID(String ID) {
        return user.getByID(ID);
    }

    @Override
    public int DuplicateChecking(String phone) {
        return user.DuplicateChecking(phone);
    }

    @Override
    public boolean addUser(User user) {
        return this.user.insert(user);
    }
}
