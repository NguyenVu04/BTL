package com.project.backend.security;

import java.util.List;

import com.project.backend.firebase.CollectionName;
import com.project.backend.model.Model;

@CollectionName("SecurityDetails")
public class AuthenticationDetails extends Model {
    private String email;
    private String password;
    private List<String> role;
    private String userId;
    protected AuthenticationDetails() {}
    public AuthenticationDetails(String username, String password, List<UserRole> userRole, String userId) {
        this.email = username;
        this.password = BackendAuthenticationProvider.encoder
                                                     .encode(password);
        this.role = userRole.stream()
                            .map(role -> role.name())
                            .toList();
        this.userId = userId;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public List<String> getRole() {
        return role;
    }
    public void setRole(List<UserRole> userRole) {
        this.role = userRole.stream()
                            .map(role -> role.name())
                            .toList();
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
