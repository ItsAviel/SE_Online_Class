package org.example.models;

import java.util.List;

public class LessonDTO {
    private Long id;
    private String title;
    private List<UserDTO> students;
    private String teacherName;

    public LessonDTO(Long id, String title, List<UserDTO> students) {
        this.id = id;
        this.title = title;
        this.students = students;
    }

    public LessonDTO(Long id, String title, List<UserDTO> students, String teacherName) {
        this.id = id;
        this.title = title;
        this.students = students;
        this.teacherName = teacherName;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public List<UserDTO> getStudents() { return students; }
    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}
