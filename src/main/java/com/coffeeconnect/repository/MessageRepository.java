package com.coffeeconnect.repository;

import com.coffeeconnect.entity.Conversation;
import com.coffeeconnect.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByConversationOrderBySentAtAsc(Conversation conversation);
    
    @Query("SELECT COUNT(m) FROM Message m WHERE m.conversation = :conversation AND m.isRead = false AND m.sender.id != :userId")
    long countUnreadMessages(@Param("conversation") Conversation conversation, @Param("userId") Long userId);
    
    long count();
    
    @Query("SELECT m FROM Message m WHERE m.conversation = :conversation ORDER BY m.sentAt DESC")
    List<Message> findLastMessageByConversation(@Param("conversation") Conversation conversation);
}
