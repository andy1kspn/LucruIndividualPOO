package com.backend.li.controller;

import com.backend.li.model.MessageEntity;
import com.backend.li.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public ResponseEntity<List<MessageEntity>> getAllMessages() {
        List<MessageEntity> messages = messageService.getAllMessages();
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/{messageId}")
    public ResponseEntity<MessageEntity> getMessageById(@PathVariable Long messageId) {
        return messageService.getMessageById(messageId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MessageEntity> createMessage(@RequestBody MessageEntity messageEntity) {
        MessageEntity createdMessage = messageService.createMessage(messageEntity);
        return ResponseEntity.ok(createdMessage);
    }

    @PutMapping("/{messageId}")
    public ResponseEntity<MessageEntity> updateMessage(@PathVariable Long messageId, @RequestBody MessageEntity updatedMessage) {
        return messageService.getMessageById(messageId)
                .map(existingMessage -> {
                    existingMessage.setId_utilizator(updatedMessage.getId_utilizator());
                    existingMessage.setMesaj(updatedMessage.getMesaj());

                    MessageEntity savedMessage = messageService.createMessage(existingMessage);
                    return ResponseEntity.ok(savedMessage);
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{messageId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long messageId) {
        messageService.deleteMessage(messageId);
        return ResponseEntity.noContent().build();
    }

}
