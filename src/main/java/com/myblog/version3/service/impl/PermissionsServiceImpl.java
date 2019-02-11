package com.myblog.version3.service.impl;

import com.myblog.version3.entity.Permissions;
import com.myblog.version3.mapper.PermissionsMapper;
import com.myblog.version3.service.permissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionsServiceImpl implements permissionsService {

    @Autowired
    PermissionsMapper mapper;

    @Override
    public Permissions getByUid(String Uid) {
        return mapper.getByUid(Uid);
    }

    @Override
    public boolean add(Permissions permissions) {
        return mapper.Insert(permissions);
    }
}
