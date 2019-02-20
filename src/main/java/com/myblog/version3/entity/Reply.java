package com.myblog.version3.entity;

import java.util.Date;

public class Reply {
    private String ID;
    private String Cid;
    private Comment comment;
    private String parent_reply_id;
    private Message parent_reply;
    private String reply_id;
    private Message reply;
    private String Content;
    private Date time;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCid() {
        return Cid;
    }

    public void setCid(String cid) {
        Cid = cid;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public String getParent_reply_id() {
        return parent_reply_id;
    }

    public void setParent_reply_id(String parent_reply_id) {
        this.parent_reply_id = parent_reply_id;
    }

    public Message getParent_reply() {
        return parent_reply;
    }

    public void setParent_reply(Message parent_reply) {
        this.parent_reply = parent_reply;
    }

    public String getReply_id() {
        return reply_id;
    }

    public void setReply_id(String reply_id) {
        this.reply_id = reply_id;
    }

    public Message getReply() {
        return reply;
    }

    public void setReply(Message reply) {
        this.reply = reply;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
