package inviteme.restfull.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import inviteme.restfull.entiity.Galery;
import inviteme.restfull.entiity.Projects;

public interface GaleryRepository extends JpaRepository<Galery, String>{
        Optional<Galery> findByProject(Projects project);

}
