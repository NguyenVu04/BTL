package com.project.backend.security;

import java.util.List;

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
    private List<UserRole> userRole;
    private List<String> role;
    private String userId;
    public AuthenticationDetails(String username, String password, List<UserRole> userRole) {
        this.email = username;
        this.password = password;
        this.userRole = userRole;
        this.role = userRole.stream()
                            .map(role -> role.name())
                            .toList();
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    @Exclude
    public List<UserRole> getUserRole() {
        return userRole;
    }
    public void setUserRole(List<UserRole> userRole) {
        this.userRole = userRole;
        this.role = userRole.stream()
                            .map(role -> role.name())
                            .toList();
    }
    public List<String> getRole() {
        return role;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
}
