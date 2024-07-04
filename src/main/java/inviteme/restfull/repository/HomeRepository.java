package inviteme.restfull.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import inviteme.restfull.entiity.Home;
import inviteme.restfull.entiity.Projects;
import java.util.Optional;

@Repository
public interface HomeRepository extends JpaRepository<Home, String>{
        Optional<Home> findByProject(Projects project);
}
