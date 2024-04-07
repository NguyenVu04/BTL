package com.project.backend.security;

import com.project.backend.firebase.CollectionName;
import com.project.backend.model.Model;

/**
 * Specifies the name of the Firebase Firestore collection that stores
 * security-related details.
 */
@CollectionName("SecurityDetails")
public class AuthenticationDetails extends Model {
    // User's email address
    private String email;
    // User's hashed password
    private String password;
    // User's role (TEACHER, STUDENT)
    private String role;
    // Unique identifier for the user
    private String userId;
    // Protected no-argument constructor for Firestore serialization
    protected AuthenticationDetails() {}

    // Constructor to create an AuthenticationDetails object with email, password, role, and userId
    public AuthenticationDetails(String email, String password, UserRole userRole, String userId) {
        this.email = email;
        // Encode the password using the BCryptPasswordEncoder from BackendSecurityConfiguration
        this.password = BackendSecurityConfiguration.encoder.encode(password);
        this.role = userRole.name();
        this.userId = userId;
    }
    // Getter for the password
    public String getPassword() {
        return password;
    }

    // Setter for the password
    public void setPassword(String password) {
        this.password = password;
    }

    // Getter for the role
    public String getRole() {
        return role;
    }

    // Setter for the role, accepting a UserRole enum
    public void setRole(UserRole userRole) {
        this.role = userRole.name();
    }

    // Getter for the email
    public String getEmail() {
        return email;
    }

    // Setter for the email
    public void setEmail(String email) {
        this.email = email;
    }

    // Getter for the userId
    public String getUserId() {
        return userId;
    }

    // Setter for the userId
    public void setUserId(String userId) {
        this.userId = userId;
    }    
}