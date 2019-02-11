package com.myblog.version3.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "用户实体类" , description = "作为用户登录使用的验证实体类")
public class User implements Serializable {

    @ApiModelProperty(hidden = true)
    private String ID;

    @ApiModelProperty(value = "登录密码" , name = "password" , required = true)
    private String password;

    @ApiModelProperty(value = "登录的手机号" , name = "phone" ,required = true)
    private String phone;


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
