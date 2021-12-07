package com.website.demo.API.message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {




    @Query(value = "select new com.website.demo.API.message.MessageDto(m.content, m.date, m.sender.id, m.receiver.id) from Message m where (m.sender.id = ?2 and m.receiver.id = ?1) or (m.sender.id = ?1 and m.receiver.id = ?2) order by m.date asc")
    List<MessageDto> findMessagesBy(Integer receiverId, Integer senderId);

    @Query(value = "select new com.website.demo.API.message.MessageDto(m.content, m.date, m.sender.id, m.receiver.id) from Message m where m.receiver.id = ?1 ")
    List<MessageDto> findMessagesForUser(Integer id);
}
