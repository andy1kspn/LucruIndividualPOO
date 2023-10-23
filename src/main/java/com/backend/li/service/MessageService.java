package com.backend.li.service;

import com.backend.li.model.MessageEntity;
import com.backend.li.repository.MessageRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<MessageEntity> getAllMessages() {
        return messageRepository.findAll();
    }

    public Optional<MessageEntity> getMessageById(Long messageId) {
        return messageRepository.findById(messageId);
    }

    public MessageEntity createMessage(MessageEntity messageEntity) {
        String transformedMessage = messageEntity.getMesaj().toUpperCase();
        messageEntity.setMesaj(transformedMessage);

        return messageRepository.save(messageEntity);
    }

    public void deleteMessage(Long messageId) {
        messageRepository.deleteById(messageId);
    }


}
