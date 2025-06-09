package org.example.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Teacher extends User {

     private String subject;


    public Teacher() {}

    public Teacher(Long id,Long password, String name, String email) {
        super(id,password, name, email);
        this.subject = "Not Assigned";
    }

    public Teacher(Long id,Long password, String name, String email,String subject) {
        super(id,password, name, email);
        this.subject = subject;

    }


    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

}
