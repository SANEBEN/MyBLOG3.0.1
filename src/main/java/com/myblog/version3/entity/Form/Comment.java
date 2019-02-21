package com.myblog.version3.entity.Form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class Comment {

    private String Uid;
    private String Aid;
    @NotBlank(message = "评论不能为空")
    @Pattern(regexp = "[a-zA-Z0-9\\u4e00-\\u9fa5]{1,255}" ,message = "评论中不能含有特殊字符，且最多255个字符")
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
