package com.myblog.version3.entity;

import io.swagger.annotations.ApiModel;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@ApiModel(value = "文章实体类", description = "用于存储文章信息")
public class Article {
    private String ID;
    private String Cid;
    private Category category;
    private String Uid;
    private Message user;
    private String title;
    private String URL;
    private String Content;
    private Date createdTime;
    private Date changeTime;
    private int hits;
    private Boolean isPrivate;
    private Boolean allowComment;
    private int status;

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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getContent(){
        return Content;
    }

    public void setContent(String URL) {
        try {
            File file = new File(URL);
            if (file.exists()) {
                InputStreamReader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(reader);
                String temp = null;
                StringBuilder content = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    content.append(temp);
                }
                reader.close();
                Content = content.toString().trim();
            } else {
                Content = "未找到指定文件，请稍后重试，或联系网站管理员了解相关情况";
            }
        } catch (IOException e) {
            e.printStackTrace();
            Content = "读取文件出错，请稍后重试，或联系网站管理员了解相关情况";
        }
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public Boolean getAllowComment() {
        return allowComment;
    }

    public void setAllowComment(Boolean allowComment) {
        this.allowComment = allowComment;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }
}
