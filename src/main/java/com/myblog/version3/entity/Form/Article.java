package com.myblog.version3.entity.Form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class Article {

    @NotBlank(message = "文章标题不能为空")
    private String title;

    @NotEmpty(message = "文章内容不能为空")
    private String content;

    private String Cid;

    private String Uid;

    private String Aid;

    private Boolean allowComment;
    private Boolean isPrivate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCid() {
        return Cid;
    }

    public void setCid(String cid) {
        Cid = cid;
    }

    public Boolean getAllowComment() {
        return allowComment;
    }

    public void setAllowComment(Boolean allowComment) {
        this.allowComment = allowComment;
    }

    public Boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }

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
}
