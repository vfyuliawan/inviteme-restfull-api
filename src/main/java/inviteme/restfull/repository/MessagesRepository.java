package inviteme.restfull.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import inviteme.restfull.entiity.Message;
import inviteme.restfull.entiity.Messages;
import java.util.List;


public interface MessagesRepository extends JpaRepository<Messages, String>{
    List<Messages> findByMessage(Message message);
    
} 

