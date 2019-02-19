package com.myblog.version3.entity;

import java.util.Date;

public class UserActivity {
    private String ID;
    private String Uid;
    private String action;
    private String object_id;
    private Object object;
    private String operation_object_id;
    private Object operation_object;
    private Date created_time;
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

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getOperation_object_id() {
        return operation_object_id;
    }

    public void setOperation_object_id(String operation_object_id) {
        this.operation_object_id = operation_object_id;
    }

    public Object getOperation_object() {
        return operation_object;
    }

    public void setOperation_object(Object operation_object) {
        this.operation_object = operation_object;
    }

    public Date getCreated_time() {
        return created_time;
    }

    public void setCreated_time(Date created_time) {
        this.created_time = created_time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
