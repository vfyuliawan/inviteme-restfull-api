package inviteme.restfull.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import inviteme.restfull.entiity.Messages;
import inviteme.restfull.entiity.Projects;

import java.util.List;


public interface MessagesRepository extends JpaRepository<Messages, String>{
    List<Messages> findByProjectOrderByCreateDateDesc(Projects project);
    
} 

