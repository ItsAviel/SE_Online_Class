package org.example.repositories;

import org.example.models.Lesson;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LessonRepository extends CrudRepository<Lesson,Long> {

    boolean findByTitle(String title);


    @Override
    List<Lesson> findAll();

    List<Lesson> findByTeacherId(Long teacherId);




}
