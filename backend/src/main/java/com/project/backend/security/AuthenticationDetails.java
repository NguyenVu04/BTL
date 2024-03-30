package com.project.backend.security;

import com.google.cloud.firestore.annotation.Exclude;
import com.project.backend.firebase.CollectionName;
import com.project.backend.model.Model;

@CollectionName("AuthDetails")
public class AuthenticationDetails extends Model {
    public enum UserRole {
        TEACHER, STUDENT
    }
    private String email;
    private String password;
    @Exclude
    private UserRole userRole;
    private String role;
    public AuthenticationDetails(String username, String password, UserRole userRole) {
        this.email = username;
        this.password = password;
        this.userRole = userRole;
        this.role = userRole.name();
    }
    public String getUsername() {
        return email;
    }
    public void setUsername(String username) {
        this.email = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    @Exclude
    public UserRole getUserRole() {
        return userRole;
    }
    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
        this.role = userRole.name();
    }
    public String getRole() {
        return role;
    }
}
