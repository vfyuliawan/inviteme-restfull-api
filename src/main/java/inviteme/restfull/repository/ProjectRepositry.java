package inviteme.restfull.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import inviteme.restfull.entiity.Projects;
import inviteme.restfull.entiity.Theme;
import inviteme.restfull.entiity.User;
import java.util.Optional;
import java.util.List;;

@Repository

public interface ProjectRepositry extends JpaRepository<Projects, String> {

        Page<Projects> findByTitleContainingAndUser(String title, User user, Pageable pageable);

        Page<Projects> findByUser(User user, Pageable pageable);

        Optional<Projects> findById(String id);

        @Query("SELECT p FROM Projects p JOIN p.theme t WHERE t.slug = :slug")
        Optional<Projects> findByThemeSlug(@Param("slug") String slug);

}
