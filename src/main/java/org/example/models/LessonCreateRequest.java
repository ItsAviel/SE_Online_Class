package org.example.models;

public class LessonCreateRequest {
    private String title;
    private Long teacherId;

    public LessonCreateRequest() {}

    public LessonCreateRequest(String title, Long teacherId) {
        this.title = title;
        this.teacherId = teacherId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }
}
