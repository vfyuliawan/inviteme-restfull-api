package inviteme.restfull.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import inviteme.restfull.entiity.BraidInfo;
import inviteme.restfull.entiity.Projects;
import java.util.*;;


public interface BraidInfoRepository extends JpaRepository<BraidInfo, String>{
                Optional<BraidInfo> findByProject(Projects project);

}
