package inviteme.restfull.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import inviteme.restfull.entiity.Projects;
import inviteme.restfull.entiity.Theme;
import java.util.Optional;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, String> {
        Optional<Theme> findByProject(Projects project);

}
