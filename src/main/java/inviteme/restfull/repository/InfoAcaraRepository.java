package inviteme.restfull.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import inviteme.restfull.entiity.Acara;
import inviteme.restfull.entiity.Projects;
import java.util.*;;

public interface InfoAcaraRepository extends JpaRepository<Acara, String> {
            Optional<Acara> findByProject(Projects project);

}
