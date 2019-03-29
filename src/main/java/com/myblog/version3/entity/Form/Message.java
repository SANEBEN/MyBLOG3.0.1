package com.myblog.version3.entity.Form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Message {
    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^[a-zA-Z\\u4e00-\\u9fa50-9_]{3,15}$", message = "用户名不符合格式")
    private String userName;

    @NotBlank(message = "邮箱不能为空")
    @Pattern(regexp = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?", message = "请输入正确的邮箱地址")
    private String email;

    @Size(max = 100 ,min = 0)
    private String introduce;

    private String Uid;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }
}
