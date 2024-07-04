package inviteme.restfull.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import inviteme.restfull.entiity.Cover;
import inviteme.restfull.entiity.Gift;
import inviteme.restfull.entiity.Projects;

import java.util.*;;


public interface GiftRepository extends JpaRepository<Gift, String>{
        Optional<Gift> findByProject(Projects project);
}
