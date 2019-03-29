package com.myblog.version3.entity.Form;

import org.springframework.web.multipart.MultipartFile;

public class ArticleImage {
    private String Aid;
    private MultipartFile image;

    public String getAid() {
        return Aid;
    }

    public void setAid(String aid) {
        Aid = aid;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
