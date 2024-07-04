package inviteme.restfull.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import inviteme.restfull.entiity.Projects;
import inviteme.restfull.entiity.Story;
import java.util.*;;

public interface StoryRepository extends JpaRepository<Story, String>{
            Optional<Story> findByProject(Projects project);

}
