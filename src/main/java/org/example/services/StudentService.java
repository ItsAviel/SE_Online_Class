package org.example.services;

import org.example.models.Profile;
import org.example.models.Student;
import org.example.repositories.ProfileRepository;
import org.example.repositories.StudentRepository;
import org.example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    UserRepository userRepository;


    public String createStudent(Student student) {
        if (student.getId() <= 0) {
            return "Error: student ID is invalid";
        }
        if ( userRepository.existsById(student.getId()) || existsById(student.getId())) {
            return "Error: student ID already exists";
        }
        if (student.getPassword() == null || student.getPassword() < 100 ) {
            return "Error: password must be longer than 3 digits";
        }
        if (student.getName() == null || student.getName().trim().length() < 2
                || student.getName().length() > 20 ||
                !student.getName().matches("[a-zA-Zא-ת ]+")) {
            return "Error: Invalid student name";
        }
        studentRepository.save(student);

        // Create a new profile for the student
        Profile profile = new Profile();
        profile.setRole("student");
        profile.setBio("New student profile");
        profile.setUser_id(student.getId());
        profile.setUsername(student.getName());
        profile.setEmail(student.getEmail());
        profileRepository.save(profile);

        return "student created successfully";
    }



    // -- Help methods:

    public boolean existsById(Long studentId) {
        return studentRepository.existsById(studentId);
    }

    public Student getStudentById(Long id) {
       return studentRepository.findById(id)
               .orElseThrow(() -> new RuntimeException("Student not found"));
    }





    // -- Temp
//
//    public List<Student> getAllStudents() {
//        List<Student> students = studentRepository.findAll();
//        if (students.isEmpty()) {
//            throw new RuntimeException("No students found");
//        }
//        return students;
//    }



}
