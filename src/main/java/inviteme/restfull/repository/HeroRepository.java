package inviteme.restfull.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import inviteme.restfull.entiity.Hero;
import inviteme.restfull.entiity.Projects;

import java.util.Optional;


public interface HeroRepository extends JpaRepository<Hero, String> {
    Optional<Hero> findByProject(Projects project);

}
