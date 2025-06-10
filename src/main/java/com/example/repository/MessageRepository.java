package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.*;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    public Message findMessageByMessageId(Integer messageId);

    public int deleteMessageByMessageId(Integer messageId);

    public List<Message> findMessageByPostedBy(Integer postedById);
}
