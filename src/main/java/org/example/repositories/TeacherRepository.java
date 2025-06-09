package org.example.repositories;

import org.example.models.Teacher;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TeacherRepository extends CrudRepository<Teacher,Long> {

    @Override
    List<Teacher> findAll();
}
