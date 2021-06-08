package com.s4.spring.Repository;

import com.s4.spring.Entity.Group;
import com.s4.spring.Entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {

    @Query("Select m from Message m where (m.sender.userId=?1 or m.receiver.userId=?1) and (m.sender.userId=?2 or m.receiver.userId=?2) order by m.date asc")
    Collection<Message> getConversation(long user1Id, long user2Id);

}