package com.myblog.version3.entity;

import java.util.Date;

public class Reply {
    private String ID;
    private String Cid;
    private String parent_reply_id;
    private String reply_id;
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
