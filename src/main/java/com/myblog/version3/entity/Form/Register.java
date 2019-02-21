package com.myblog.version3.entity.Form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class Register implements Serializable {

    @NotBlank(message = "电话号码不能为空")
    @Pattern(regexp = "^1(3|4|5|7|8)\\d{9}$", message = "手机号格式错误")
    private String phone;

    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^[a-zA-Z\\u4e00-\\u9fa50-9_]{3,15}$",message = "用户名不符合格式")
    private String userName;

    @NotBlank(message = "密码不能为空")
    private String Rpassword;

    @NotBlank(message = "请输入确认密码")
    private String Lpassword;

    @NotBlank(message = "验证码不能为空")
    private String verification;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRpassword() {
        return Rpassword;
    }

    public void setRpassword(String rpassword) {
        Rpassword = rpassword;
    }

    public String getLpassword() {
        return Lpassword;
    }

    public void setLpassword(String lpassword) {
        Lpassword = lpassword;
    }

    public String getVerification() {
        return verification;
    }

    public void setVerification(String verification) {
        this.verification = verification;
    }
}
