package com.myblog.version3.entity.Form;

import javax.validation.constraints.NotBlank;

public class Reply {
    private String Cid;
    private String parent_reply_id;
    private String reply_id;
    @NotBlank(message = "回复内容不能为空")
    private String content;

    public String getCid() {
        return Cid;
    }

    public void setCid(String cid) {
        Cid = cid;
    }

    public String getParent_reply_id() {
        return parent_reply_id;
    }

    public void setParent_reply_id(String parent_reply_id) {
        this.parent_reply_id = parent_reply_id;
    }

    public String getReply_id() {
        return reply_id;
    }

    public void setReply_id(String reply_id) {
        this.reply_id = reply_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
