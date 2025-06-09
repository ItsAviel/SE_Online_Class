package org.example.services;


import org.example.models.Profile;
import org.example.models.Student;
import org.example.models.Teacher;
import org.example.repositories.ProfileRepository;
import org.example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.repositories.TeacherRepository;

import java.util.List;

@Service
public class TeacherService {

    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    UserRepository userRepository;




    public String createTeacher(Teacher teacher) {
        if (teacher.getId() <= 0|| teacher.getId() > 999999999 || teacher.getId() == null) {
            return "Error: teacher ID is invalid.";
        }
        if (userRepository.existsById(teacher.getId()) || existsById(teacher.getId())) {
            return "Error: teacher ID already exists.";
        }
        if (teacher.getPassword() == null || teacher.getPassword() < 100 ) {
            return "Error: password must be longer than 3 digits";
        }
        if (teacher.getName() == null || teacher.getName().trim().length() < 2
                || teacher.getName().length() > 20 ||
                !teacher.getName().matches("[a-zA-Zא-ת ]+")) {
            return "Error: Invalid teacher name";
        }
        teacherRepository.save(teacher);

        // Create a new profile for the teacher
        Profile profile = new Profile();
        profile.setRole("teacher");
        profile.setBio("New teacher profile");
        profile.setUser_id(teacher.getId());
        profile.setUsername(teacher.getName());
        profile.setEmail(teacher.getEmail());
        profile.setSubject(teacher.getSubject());
        profileRepository.save(profile);

        return "Teacher created successfully";
    }



    // -- Help methods:

    public boolean existsById(Long id) {
        return teacherRepository.existsById(id);
    }

    public Teacher getTeacherById(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
    }

}
