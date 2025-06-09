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




    // -- TEMP

//    @GetMapping
//    public boolean existsById(@RequestParam Long teacherId) {
//        return teacherService.existsById(teacherId);
//    }
//
//    @GetMapping("/all")
//    public List<Teacher> getAllTeachers() {
//        return teacherService.getAllTeachers();
//    }
//
//    @GetMapping("/{id}")
//    public Teacher getTeacherById(Long id) {
//        return teacherService.getTeacherById(id);
//    }
//
//    @GetMapping("/subject/{subject}")
//    public List<Teacher> getTeachersBySubject(String subject) {
//        return teacherService.getTeachersBySubject(subject);
//    }

}
