package com.myblog.version3.entity.Form;

import org.springframework.web.multipart.MultipartFile;

public class HeadPortrait {
    private MultipartFile HeadPortrait;
    private String Uid;

    public MultipartFile getHeadPortrait() {
        return HeadPortrait;
    }

    public void setHeadPortrait(MultipartFile headPortrait) {
        HeadPortrait = headPortrait;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }
}
