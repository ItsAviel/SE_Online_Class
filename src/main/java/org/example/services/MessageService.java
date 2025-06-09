package org.example.services;

import org.example.models.Message;
import org.example.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;


    public String sendMessage(Message message) {
        if (message.getText() == null || message.getText().isEmpty()
        || message.getSender() == null || message.getReceiver() == null) {
            return "Error while sending message: Missing required fields";
        }
        messageRepository.save(message);
        return "Message sent successfully";
    }
    public List<Message> receiveMessages() {
        return messageRepository.findAll();
    }
    public void deleteMessage(Long id) {
        if (!messageRepository.existsById(id)) {
            throw new IllegalArgumentException("Message with id " + id + " does not exist");
        }
        messageRepository.deleteById(id);
    }

}
