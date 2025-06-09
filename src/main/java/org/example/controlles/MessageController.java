package org.example.controlles;

import org.example.models.Message;
import org.example.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    MessageService messageService;

     @PostMapping("/send")
     public String sendMessage(@RequestBody Message message) {
         return messageService.sendMessage(message);
     }

     @GetMapping("/receive")
     public List<Message> receiveMessages() {
         return messageService.receiveMessages();
     }

     @DeleteMapping("/{id}")
     public String deleteMessage(@PathVariable Long id) {
         messageService.deleteMessage(id);
         return "Message deleted successfully";
     }



}
