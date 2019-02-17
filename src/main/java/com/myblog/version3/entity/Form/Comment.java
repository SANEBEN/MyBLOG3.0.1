package com.myblog.version3.entity.Form;

import javax.validation.constraints.NotBlank;

public class Comment {

    private String Uid;
    private String Aid;
    @NotBlank(message = "评论不能为空")
    private String content;

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getAid() {
        return Aid;
    }

    public void setAid(String aid) {
        Aid = aid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
