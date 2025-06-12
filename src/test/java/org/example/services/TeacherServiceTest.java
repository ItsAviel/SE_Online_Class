package org.example.services;

import org.example.models.Teacher;
import org.example.repositories.ProfileRepository;
import org.example.repositories.TeacherRepository;
import org.example.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TeacherServiceTest {

    @Mock
    private TeacherRepository teacherRepository;
    @Mock
    private ProfileRepository profileRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TeacherService teacherService;

    public TeacherServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateTeacher_InvalidId() {
        System.out.println("Running testCreateTeacher_InvalidId");
        Teacher t = new Teacher();
        t.setId(1234567L);
        t.setName("Test");
        t.setPassword(123L);
        String result = teacherService.createTeacher(t);
        assertEquals("Error: teacher ID is invalid.", result, "Failed test");
    }

    @Test
    public void testCreateTeacher_InvalidPassword() {
        System.out.println("Running testCreateTeacher_InvalidPassword");
        Teacher t = new Teacher();
        t.setId(123456789L);
        t.setName("Test");
        t.setPassword(50L);
        String result = teacherService.createTeacher(t);
        assertEquals("Error: password must be longer than 3 digits", result, "Failed test");
    }

    @Test
    public void testCreateTeacher_InvalidName() {
        System.out.println("Running testCreateTeacher_InvalidName");
        Teacher t = new Teacher();
        t.setId(123456789L);
        t.setPassword(123L);
        t.setName("1");
        String result = teacherService.createTeacher(t);
        assertEquals("Error: Invalid teacher name", result, "Failed test");
    }



    @Test
    public void testCreateTeacher_Success() {
        System.out.println("Running testCreateTeacher_Success");
        Teacher t = new Teacher();
        t.setId(123456789L);
        t.setName("Test Teacher");
        t.setPassword(1234L);
        t.setEmail("TestMail@gmail.com");
        t.setSubject("Math");

        when(userRepository.existsById(t.getId())).thenReturn(false);
        when(teacherRepository.existsById(t.getId())).thenReturn(false);
        String result = teacherService.createTeacher(t);
        assertEquals("Teacher created successfully", result, "Failed test");
    }
}
