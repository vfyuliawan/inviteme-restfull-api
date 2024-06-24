package inviteme.restfull.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import inviteme.restfull.entiity.Projects;

@Repository

public interface ProjectRepositry extends JpaRepository<Projects, String>{
    
}
