package inviteme.restfull.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import inviteme.restfull.entiity.Message;
import inviteme.restfull.entiity.Projects;

import java.util.Optional;



public interface MessageProjectRepository extends JpaRepository<Message, String>{
    Optional<Message> findByProjectId(String projectId);
    
} 
