package com.project.backend.security;

import com.project.backend.firebase.CollectionName;
import com.project.backend.model.Model;

@CollectionName("SecurityDetails")
public class AuthenticationDetails extends Model {
    private String email;
    private String password;
    private String role;
    private String userId;
    protected AuthenticationDetails() {}
    public AuthenticationDetails(String email, String password, UserRole userRole, String userId) {
        this.email = email;
        this.password = BackendSecurityConfiguration.encoder
                                                    .encode(password);
        this.role = userRole.name();
        this.userId = userId;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRole() {
        return role;
    }
    public void setRole(UserRole userRole) {
        this.role = userRole.name();
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
