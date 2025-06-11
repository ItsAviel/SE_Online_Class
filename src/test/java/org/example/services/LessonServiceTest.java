package org.example.services;

import org.example.models.*;
import org.example.repositories.LessonRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LessonServiceTest {

    @Mock
    private LessonRepository lessonRepository;
    @Mock
    private StudentService studentService;
    @Mock
    private TeacherService teacherService;

    @InjectMocks
    private LessonService lessonService;

    public LessonServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testJoinToLesson_LessonNotFound() {
        System.out.println("Running testJoinToLesson_LessonNotFound");
        when(lessonRepository.findById(1L)).thenReturn(Optional.empty());
        String result = lessonService.joinToLesson(1L, 2L);
        assertEquals("Lesson not found", result,"Failed test");

    }

    @Test
    public void testCreateLesson_InvalidTitle() {
        System.out.println("Running testCreateLesson_InvalidTitle");
        String result = lessonService.createLesson("!!", 1L);
        assertEquals("Error: Invalid lesson title", result,"Failed test");


    }

    @Test
    public void testDeleteLesson_NotFound() {
        System.out.println("Running testDeleteLesson_NotFound");
        when(lessonRepository.existsById(10L)).thenReturn(false);
        String result = lessonService.deleteLesson(10L);
        assertEquals("Error: Lesson not found", result,"Failed test");
    }


    @Test
    public void testGetAllLessons() {
        System.out.println("Running testGetAllLessons");
        List<Lesson> lessons = new ArrayList<>();
        Lesson lesson1 = new Lesson();
        lesson1.setId(1L);
        lesson1.setTitle("Math");
        lesson1.setTeacherId(3L);
        lesson1.setStudents(new ArrayList<>());
        Lesson lesson2 = new Lesson();
        lesson2.setId(2L);
        lesson2.setTitle("Science");
        lesson2.setTeacherId(3L);
        lesson2.setStudents(new ArrayList<>());
        lessons.add(lesson1);
        lessons.add(lesson2);
        when(lessonRepository.findAll()).thenReturn(lessons);
        when(teacherService.existsById(3L)).thenReturn(true);
        when(teacherService.getTeacherById(3L)).thenReturn(new Teacher());
        List<LessonDTO> result = lessonService.getAllLessons();
        assertEquals(2, result.size(), "Failed to retrieve all lessons");
        assertEquals("Math", result.get(0).getTitle(), "First lesson title mismatch");
        assertEquals("Science", result.get(1).getTitle(), "Second lesson title mismatch");
    }

    @Test
    public void testGetAllLessonsForTeacher() {
        System.out.println("Running testGetAllLessonsForTeacher");
        List<Lesson> lessons = new ArrayList<>();
        Lesson lesson1 = new Lesson();
        lesson1.setId(1L);
        lesson1.setTitle("Math");
        lesson1.setTeacherId(3L);
        lesson1.setStudents(new ArrayList<>());
        Lesson lesson2 = new Lesson();
        lesson2.setId(2L);
        lesson2.setTitle("Science");
        lesson2.setTeacherId(3L);
        lesson2.setStudents(new ArrayList<>());
        lessons.add(lesson1);
        lessons.add(lesson2);

        when(lessonRepository.findAll()).thenReturn(lessons);
        when(teacherService.existsById(3L)).thenReturn(true);
        when(teacherService.getTeacherById(3L)).thenReturn(new Teacher());

        List<LessonDTO> result = lessonService.getAllLessonsForTeacher(3L);

        assertEquals(2, result.size(), "Failed to retrieve all lessons for teacher");
        assertEquals("Math", result.get(0).getTitle(), "First lesson title mismatch");
        assertEquals("Science", result.get(1).getTitle(), "Second lesson title mismatch");
    }
}
