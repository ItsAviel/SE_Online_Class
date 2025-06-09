package org.example.controlles;

import org.example.models.Lesson;
import org.example.models.LessonCreateRequest;
import org.example.models.LessonDTO;
import org.example.models.LessonJoinRequest;
import org.example.services.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lesson")
public class LessonController {

    @Autowired
    LessonService lessonService;


    // -- Mapping for students:

    @GetMapping
    public List<LessonDTO> getAllLessons() {
        return lessonService.getAllLessons();
    }

    @PostMapping("/join")
    public String joinToLesson(@RequestBody LessonJoinRequest lessonJoinRequest){
        return lessonService.joinToLesson(lessonJoinRequest.getLessonId(), lessonJoinRequest.getUserId());
    }

    @DeleteMapping("/delete")
    public String removeStudentFromLesson(@RequestBody LessonJoinRequest lessonJoinRequest) {
        return lessonService.removeStudentFromLesson(lessonJoinRequest.getLessonId(), lessonJoinRequest.getUserId());
    }



    // -- Mapping for teachers:

    @GetMapping("/{teacherId}")
    public List<LessonDTO> getAllLessonsForTeacher(@PathVariable Long teacherId) {
        return lessonService.getAllLessonsForTeacher(teacherId);
    }

    @PostMapping
    public String createLesson(@RequestBody LessonCreateRequest lessonCreateRequest) {
        return lessonService.createLesson(lessonCreateRequest.getTitle(), lessonCreateRequest.getTeacherId());
    }

    @DeleteMapping("/deleteLesson/{lessonId}")
    public String deleteLesson(@PathVariable Long lessonId) {
        return lessonService.deleteLesson(lessonId);
    }
}
