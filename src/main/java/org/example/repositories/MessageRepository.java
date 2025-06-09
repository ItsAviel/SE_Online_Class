package org.example.repositories;

import org.example.models.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {
    @Override
    List<Message> findAll();

}
