package com.myblog.version3.entity;

public class UserActivity {
    private String ID;
    private String Uid;
    private String action;
    private String object_id;
    private String object_type;
    private String parent_object_id;
    private String parent_object_type;
    private String reply_id;
    private String parent_reply_id;
    private String created_time;
    private String text;

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

    public String getObject_type() {
        return object_type;
    }

    public void setObject_type(String object_type) {
        this.object_type = object_type;
    }

    public String getParent_object_id() {
        return parent_object_id;
    }

    public void setParent_object_id(String parent_object_id) {
        this.parent_object_id = parent_object_id;
    }

    public String getParent_object_type() {
        return parent_object_type;
    }

    public void setParent_object_type(String parent_object_type) {
        this.parent_object_type = parent_object_type;
    }

    public String getReply_id() {
        return reply_id;
    }

    public void setReply_id(String reply_id) {
        this.reply_id = reply_id;
    }

    public String getParent_reply_id() {
        return parent_reply_id;
    }

    public void setParent_reply_id(String parent_reply_id) {
        this.parent_reply_id = parent_reply_id;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
