package com.myblog.version3.service.impl;

import com.myblog.version3.entity.Message;
import com.myblog.version3.mapper.messageMapper;
import com.myblog.version3.service.messageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements messageService {

    @Autowired
    messageMapper messageMapper;

    @Override
    public Message getByUid(String Uid) {
        return messageMapper.getByUid(Uid);
    }
}
