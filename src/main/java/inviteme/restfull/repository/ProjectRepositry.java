package inviteme.restfull.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import inviteme.restfull.entiity.Projects;
import inviteme.restfull.entiity.User;
import java.util.Optional;;


@Repository

public interface ProjectRepositry extends JpaRepository<Projects, String>{

        Page<Projects> findByTitleContainingAndUser(String title, User user, Pageable pageable);
        Page<Projects> findByUser(User user, Pageable pageable);

        Optional<Projects> findById(String id);

}
