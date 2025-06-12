package org.example.services;

import org.example.models.Profile;
import org.example.models.Student;
import org.example.repositories.ProfileRepository;
import org.example.repositories.StudentRepository;
import org.example.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;
    @Mock
    private ProfileRepository profileRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private StudentService studentService;

    public StudentServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateStudent_InvalidId() {
        System.out.println("Running testCreateStudent_InvalidId");
        Student s = new Student();
        s.setId(0L);
        s.setName("Test");
        s.setPassword(123L);
        String result = studentService.createStudent(s);
        assertEquals("Error: student ID is invalid", result, "Failed test");
    }

    @Test
    public void testCreateStudent_InvalidPassword() {
        System.out.println("Running testCreateStudent_InvalidPassword");
        Student s = new Student();
        s.setId(123456789L);
        s.setName("Test");
        s.setPassword(50L);
        String result = studentService.createStudent(s);
        assertEquals("Error: password must be longer than 3 digits", result, "Failed test");
    }

    @Test
    public void testCreateStudent_InvalidName() {
        System.out.println("Running testCreateStudent_InvalidName");
        Student s = new Student();
        s.setId(123456789L);
        s.setPassword(123L);
        s.setName("1");
        String result = studentService.createStudent(s);
        assertEquals("Error: Invalid student name", result, "Failed test");
    }

    @Test
    public void testCreateStudent_Success() {
        System.out.println("Running testCreateStudent_Success");
        Student s = new Student();
        s.setId(123456789L);
        s.setName("Test Student");
        s.setPassword(1234L);
        s.setEmail("TestMail@gmail.com");

        when(userRepository.existsById(s.getId())).thenReturn(false);
        when(studentRepository.existsById(s.getId())).thenReturn(false);
        String result = studentService.createStudent(s);
        assertEquals("student created successfully", result, "Failed test");
    }
}
