package org.example.models;

public class AppUserRole {

    private int appUserRoleId;
    private int appUserId;
    private int appRoleId;

    public AppUserRole() {
    }

    public AppUserRole(int appUserRoleId, int appUserId, int appRoleId) {
        this.appUserRoleId = appUserRoleId;
        this.appUserId = appUserId;
        this.appRoleId = appRoleId;
    }

    public int getAppUserRoleId() {
        return appUserRoleId;
    }

    public void setAppUserRoleId(int appUserRoleId) {
        this.appUserRoleId = appUserRoleId;
    }

    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    public int getAppRoleId() {
        return appRoleId;
    }

    public void setAppRoleId(int appRoleId) {
        this.appRoleId = appRoleId;
    }
}
