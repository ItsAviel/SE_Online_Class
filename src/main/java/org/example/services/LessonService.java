package org.example.services;

import org.example.cache.CacheAlgo;
import org.example.cache.LRUCacheAlgo;
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

    // Caches
    private final CacheAlgo<Long, Lesson> lessonCache = new LRUCacheAlgo<>(100);
    private final CacheAlgo<String, List<LessonDTO>> lessonListCache = new LRUCacheAlgo<>(1);
    private final CacheAlgo<Long, List<LessonDTO>> teacherLessonsCache = new LRUCacheAlgo<>(100);


    // -- Shared methods for both students and teachers:

    public List<LessonDTO> getAllLessons() {
        if (lessonListCache.containsKey("all")) {
            return lessonListCache.get("all");
        }

        List<Lesson> unfiltered = lessonRepository.findAll();
        List<LessonDTO> filtered_lessons = new ArrayList<>();

        for (Lesson lesson : unfiltered) {
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

            filtered_lessons.add(lessonDTO);
        }

        lessonListCache.put("all", filtered_lessons);
        return filtered_lessons;
    }

    // -- Student specific methods:

    public String joinToLesson(Long lesson_id, Long user_id) {
        // Check if the lesson exists in the cache or database:
        Lesson lesson = getLessonByIdWithCache(lesson_id);
        if (lesson == null) {
            return "Lesson not found";
        }

        if (lesson.getStudents().stream().anyMatch(user -> user.getId().equals(user_id))) {
            return "User already joined this lesson";
        }

        User user = null;
        if (teacherService.existsById(user_id)) {
            user = teacherService.getTeacherById(user_id);
        } else if (studentService.existsById(user_id)) {
            user = studentService.getStudentById(user_id);
        } else {
            return "User not found";
        }

        lesson.getStudents().add(user);
        lessonRepository.save(lesson);

        // Update caches:
        lessonCache.put(lesson.getId(), lesson); // עדכון cache
        lessonListCache.remove("all");
        teacherLessonsCache.remove(lesson.getTeacherId());

        return "User " + user.getName() + " joined the lesson successfully";
    }

    public String removeStudentFromLesson(Long lesson_id, Long user_id) {
        // Check if the lesson exists in the cache or database:
        Lesson lesson = getLessonByIdWithCache(lesson_id);
        if (lesson == null) {
            return "Lesson not found";
        }

        User user = null;
        if (teacherService.existsById(user_id)) {
            user = teacherService.getTeacherById(user_id);
        } else if (studentService.existsById(user_id)) {
            user = studentService.getStudentById(user_id);
        } else {
            return "User not found";
        }

        if (!lesson.getStudents().removeIf(student -> student.getId().equals(user_id))) {
            return "User is not enrolled in this lesson";
        }

        lessonRepository.save(lesson);

        // Update caches:
        lessonCache.put(lesson.getId(), lesson); // עדכון cache
        lessonListCache.remove("all");
        teacherLessonsCache.remove(lesson.getTeacherId());

        return "User " + user.getName() + " removed from the lesson successfully";
    }


    // -- Teacher specific methods:

    public String createLesson(String title, Long teacherId) {
        if (title == null || title.trim().length() < 3
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

        // Update caches:
        lessonCache.put(lesson.getId(), lesson);
        lessonListCache.remove("all");
        teacherLessonsCache.remove(teacherId);

        return "Lesson created successfully";
    }

    public List<LessonDTO> getAllLessonsForTeacher(Long teacherId) {
        if (teacherLessonsCache.containsKey(teacherId)) {
            return teacherLessonsCache.get(teacherId);
        }

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

                LessonDTO dto = new LessonDTO(
                        lesson.getId(),
                        lesson.getTitle(),
                        students_in_lesson,
                        teacherName
                );
                filtered_lessons.add(dto);
            }
        }
        //Cache the filtered lessons for the teacher:
        teacherLessonsCache.put(teacherId, filtered_lessons);
        return filtered_lessons;
    }


    public String deleteLesson(Long lessonId) {
        if (!lessonRepository.existsById(lessonId)) {
            return "Error: Lesson not found";
        }

        //Remove the lesson from the cache and database:
        Lesson lesson = lessonCache.get(lessonId);
        Long teacherId = lesson != null ? lesson.getTeacherId() : null;

        lessonRepository.deleteById(lessonId);
        lessonCache.remove(lessonId);
        lessonListCache.remove("all");

        if (teacherId != null) {
            teacherLessonsCache.remove(teacherId);
        }

        return "Lesson deleted successfully";
    }

    // -- Help method to get a lesson with caching:
    private Lesson getLessonByIdWithCache(Long id) {
        Lesson lesson = lessonCache.get(id);
        if (lesson == null) {
            lesson = lessonRepository.findById(id).orElse(null);
            if (lesson != null) {
                lessonCache.put(id, lesson);
            }
        }
        return lesson;
    }
}
