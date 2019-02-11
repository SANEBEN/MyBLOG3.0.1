package com.myblog.version3.service;

import com.myblog.version3.entity.Permissions;

public interface permissionsService {

    Permissions getByUid(String Uid);

    boolean add(Permissions permissions);
}
