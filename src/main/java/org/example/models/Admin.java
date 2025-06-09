package org.example.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Admin extends User {

    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;

    public Admin() {}
    public Admin(Long id,Long password, String name, String email, Long adminId) {
        super(id,password, name, email);
        this.adminId = adminId;
    }


    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }



}
