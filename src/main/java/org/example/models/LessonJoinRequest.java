package org.example.models;

public class LessonJoinRequest {
    private Long lessonId;
    private Long userId;

    public LessonJoinRequest() {}
    public LessonJoinRequest(Long lessonId, Long userId) {
        this.lessonId = lessonId;
        this.userId = userId;
    }

    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


}