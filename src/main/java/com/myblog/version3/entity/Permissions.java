package com.myblog.version3.entity;

public class Permissions {
    private String ID;
    private String Uid;
    private String Role;
    private String Permissions;

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

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getPermissions() {
        return Permissions;
    }

    public void setPermissions(String permissions) {
        Permissions = permissions;
    }
}
