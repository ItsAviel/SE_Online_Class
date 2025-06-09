package org.example.controlles;


import org.example.models.Student;
import org.example.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    // -- Register student (index.html form)
    @PostMapping
    public String createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

}
