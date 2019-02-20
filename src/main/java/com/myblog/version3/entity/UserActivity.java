package com.myblog.version3.entity;

import java.util.Date;

public class UserActivity {
    private String ID;
    private String Uid;
    private Message user;
    private String action;
    private String object_id;
    private Article article;
    private Category category;
    private Comment comment;
    private Reply reply;
    private String operation_object_id;
    private Date created_time;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public Message getUser() {
        return user;
    }

    public void setUser(Message user) {
        this.user = user;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getObject_id() {
        return object_id;
    }

    public void setObject_id(String object_id) {
        this.object_id = object_id;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public Reply getReply() {
        return reply;
    }

    public void setReply(Reply reply) {
        this.reply = reply;
    }

    public String getOperation_object_id() {
        return operation_object_id;
    }

    public void setOperation_object_id(String operation_object_id) {
        this.operation_object_id = operation_object_id;
    }

    public Date getCreated_time() {
        return created_time;
    }

    public void setCreated_time(Date created_time) {
        this.created_time = created_time;
    }
}
