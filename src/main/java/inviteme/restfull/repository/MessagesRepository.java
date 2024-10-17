package inviteme.restfull.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import inviteme.restfull.entiity.Messages;
import inviteme.restfull.entiity.Projects;

import java.util.List;


@Repository
public interface MessagesRepository extends JpaRepository<Messages, String>{
    List<Messages> findByProjectOrderByCreateDateDesc(Projects project);
    
} 

