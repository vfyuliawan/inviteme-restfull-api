package inviteme.restfull.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import inviteme.restfull.entiity.Cover;
import inviteme.restfull.entiity.Projects;
import java.util.Optional;

public interface CoverRepository extends JpaRepository<Cover, String> {
        Optional<Cover> findByProject(Projects project);

}
