package inviteme.restfull.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import inviteme.restfull.entiity.Version;
import java.util.Optional;



public interface VersionRepository extends JpaRepository<Version, String> {
        Optional<Version> findById(String id);
}
