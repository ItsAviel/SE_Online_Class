package org.example.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Student extends User {



    public Student() {}

    public Student(Long id,Long password, String name, String email) {
        super(id,password, name, email);

    }



}
