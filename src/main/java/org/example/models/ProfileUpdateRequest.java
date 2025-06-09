package org.example.models;

public class ProfileUpdateRequest {

    private Long user_id;
    private String email;
    private String bio;

    public ProfileUpdateRequest() {}
    public ProfileUpdateRequest(Long user_id, String email, String bio) {
        this.user_id = user_id;
        this.email = email;
        this.bio = bio;
    }

    public Long getUser_id() { return user_id; }
    public void setUser_id(Long user_id) { this.user_id = user_id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
}

