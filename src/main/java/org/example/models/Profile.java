package org.example.models;

import jakarta.persistence.*;

@Entity
public class Profile {

    @Id
    private Long user_id;
    private String role;
    private String bio;
    private String username;
    private String email;
    private String subject;

    public Profile (){}

    public Profile (Long user_id, String bio, String role, String username, String email, String subject) {
        this.user_id = user_id;
        this.bio = bio;
        this.role = role;
    }




    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }



    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }


}
