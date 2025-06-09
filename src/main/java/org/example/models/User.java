package org.example.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user_type")
@Table(name = "users")
public class User {


    @Id
    private Long id;
    private Long password;
    private String name;
    private String email;
//    @OneToOne(cascade = CascadeType.ALL)
//    private Profile profile;
    @ManyToMany
    private List<Lesson> myLessons;


    public User() {}

    public User(Long id,Long password, String name, String email) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
        this.myLessons = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public Profile getProfile() { return profile; }
//
//    public void setProfile(Profile profile) { this.profile = profile; }

    public List<Lesson> getMyLessons() {
        return myLessons;
    }

    public void setMyLessons(List<Lesson> myLessons) {
        this.myLessons = myLessons;
    }
    public Long getPassword() {
        return password;
    }

    public void setPassword(Long password) {
        this.password = password;
    }
}
