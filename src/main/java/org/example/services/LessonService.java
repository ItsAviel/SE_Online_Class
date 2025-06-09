package org.example.services;

import org.example.models.*;
import org.example.repositories.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class LessonService {

    @Autowired
    LessonRepository lessonRepository;
    @Autowired
    StudentService studentService;
    @Autowired
    TeacherService teacherService;


    // -- Shared methods for both students and teachers:

    public List<LessonDTO> getAllLessons() {
        List<Lesson> rawLessons = lessonRepository.findAll();
        List<LessonDTO> lessonDTOList = new ArrayList<>();

        for (Lesson lesson : rawLessons) {
            List<UserDTO> studentDTOs = new ArrayList<>();
            for (User student : lesson.getStudents()) {
                UserDTO userDTO = new UserDTO(
                        student.getId(),
                        student.getName(),
                        student.getEmail()
                );
                studentDTOs.add(userDTO);
            }

            String teacherName = "Not Assigned";
            if (lesson.getTeacherId() != null && teacherService.existsById(lesson.getTeacherId())) {
                teacherName = teacherService.getTeacherById(lesson.getTeacherId()).getName();
            }

            LessonDTO lessonDTO = new LessonDTO(
                    lesson.getId(),
                    lesson.getTitle(),
                    studentDTOs,
                    teacherName
            );

            lessonDTOList.add(lessonDTO);
        }
        return lessonDTOList;
    }


    // -- Student specific methods:


    public String joinToLesson(Long lesson_id, Long user_id) {
        Lesson lesson = lessonRepository.findById(lesson_id).orElse(null);
        if (lesson == null) {
            return "Lesson not found";
        }
        if (lesson.getStudents().stream().anyMatch(user -> user.getId().equals(user_id))) {
            return "User already joined this lesson";
        }
        User user = null;
        if (teacherService.existsById(user_id) ){
            user = teacherService.getTeacherById(user_id);
        } else if ( studentService.existsById(user_id) ) {
            user = studentService.getStudentById(user_id);
        } else {
            return "User not found";
        }
        lesson.getStudents().add(user);
        lessonRepository.save(lesson);
        return "User " + user.getName() +  " joined the lesson successfully";
    }



    public String removeStudentFromLesson(Long lesson_id, Long user_id) {
        Lesson lesson = lessonRepository.findById(lesson_id).orElse(null);
        if (lesson == null) {
            return "Lesson not found";
        }

        User user = null;
        if (teacherService.existsById(user_id) ){
            user = teacherService.getTeacherById(user_id);
        } else if ( studentService.existsById(user_id) ) {
            user = studentService.getStudentById(user_id);
        } else {
            return "User not found";

        }

        if (!lesson.getStudents().removeIf(student -> student.getId().equals(user_id))) {
            return "User is not enrolled in this lesson";
        }

        lessonRepository.save(lesson);
        return "User " + user.getName() + " removed from the lesson successfully";
    }




    // -- Teacher specific methods:

    public String createLesson(String title, Long teacherId) {
        if ( title == null || title.trim().length() < 3
                || title.length() > 50
                || !title.matches("[a-zA-Zא-ת0-9 ]+")) {
            return "Error: Invalid lesson title";
        }
        if (teacherId == null || teacherId <= 0 || !teacherService.existsById(teacherId)) {
            return "Error: Invalid teacher ID";
        }

        Lesson lesson = new Lesson();
        lesson.setTitle(title);
        lesson.setTeacherId(teacherId);
        lesson.setStudents(new ArrayList<>());
        lessonRepository.save(lesson);
        return "Lesson created successfully";
    }



    public List<LessonDTO> getAllLessonsForTeacher(Long teacherId) {
        List<Lesson> unfiltered_lessons = lessonRepository.findAll();
        List<LessonDTO> filtered_lessons = new ArrayList<>();

        for (Lesson lesson : unfiltered_lessons) {
            if (lesson.getTeacherId() != null && lesson.getTeacherId().equals(teacherId)) {
                List<UserDTO> students_in_lesson = lesson.getStudents().stream()
                        .map(s -> new UserDTO(s.getId(), s.getName(), s.getEmail()))
                        .toList();

                String teacherName = null;
                if (teacherService.existsById(teacherId)) {
                    teacherName = teacherService.getTeacherById(teacherId).getName();
                }
                LessonDTO dto = new LessonDTO(lesson.getId(), lesson.getTitle(), students_in_lesson, teacherName);
                filtered_lessons.add(dto);
            }
        }
        return filtered_lessons;
    }



    public String deleteLesson(Long lessonId) {
        if (!lessonRepository.existsById(lessonId)) {
            return "Error: Lesson not found";
        }
        lessonRepository.deleteById(lessonId);
        return "Lesson deleted successfully";
    }

}
