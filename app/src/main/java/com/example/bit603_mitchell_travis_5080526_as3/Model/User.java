package com.example.bit603_mitchell_travis_5080526_as3.Model;


public class User {
    private String userId;
    private String name;
    private String email;
    private String profilePicUrl;

    // Empty constructor required for Firestore
    public User() {}

    public User(String userId, String name, String email, String profilePicUrl) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.profilePicUrl = profilePicUrl;
    }

    // Getters and setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getProfilePicUrl() { return profilePicUrl; }
    public void setProfilePicUrl(String profilePicUrl) { this.profilePicUrl = profilePicUrl; }
}