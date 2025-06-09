package org.example.controlles;


import org.example.models.Teacher;
import org.example.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    // -- Register teacher (index.html form)

    @PostMapping
    public String createTeacher(@RequestBody Teacher teacher) {
        return teacherService.createTeacher(teacher);
    }

}
