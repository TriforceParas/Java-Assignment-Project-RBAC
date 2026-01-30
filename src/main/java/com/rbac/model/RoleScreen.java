package com.rbac.model;

import java.sql.Timestamp;

public class RoleScreen {
    private int id;
    private int roleId;
    private int screenId;
    private int readPermission; 
    private int writePermission; 
    private Timestamp createdAt;

    private String roleName;
    private String screenName;
    private String screenUrl;

    public RoleScreen() {
    }

    public RoleScreen(int id, int roleId, int screenId, int readPermission, int writePermission) {
        this.id = id;
        this.roleId = roleId;
        this.screenId = screenId;
        this.readPermission = readPermission;
        this.writePermission = writePermission;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getScreenId() {
        return screenId;
    }

    public void setScreenId(int screenId) {
        this.screenId = screenId;
    }

    public int getReadPermission() {
        return readPermission;
    }

    public void setReadPermission(int readPermission) {
        this.readPermission = readPermission;
    }

    public int getWritePermission() {
        return writePermission;
    }

    public void setWritePermission(int writePermission) {
        this.writePermission = writePermission;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getScreenUrl() {
        return screenUrl;
    }

    public void setScreenUrl(String screenUrl) {
        this.screenUrl = screenUrl;
    }

    public boolean canRead() {
        return readPermission == 1;
    }

    public boolean canWrite() {
        return writePermission == 1;
    }
}
