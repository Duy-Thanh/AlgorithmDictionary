package com.algorithmswiki.apps.backend.backend.Object;

public class AccountObject {
    private Integer Id;
    private String username;
    private String fullName;
    private String email;
    private String permission;
    private String isDeletedOrDeactivated;

    public AccountObject() { }

    public AccountObject(Integer Id, String username, String fullName, String email, String permission, String isDeletedOrDeactivated) {
        this.Id = Id;
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.permission = permission;
        this.isDeletedOrDeactivated = isDeletedOrDeactivated;
    }

    // Getters and Setters
    public Integer getId() {
        return Id;
    }

    public String getUsername() {
        return username;
    }

    public String getFullname() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPermission() {
        return permission;
    }

    public String getStatus() {
        return isDeletedOrDeactivated;
    }

    public void setId(Integer id) {
        this.Id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public void setStatus(String status) {
        this.isDeletedOrDeactivated = status;
    }
}