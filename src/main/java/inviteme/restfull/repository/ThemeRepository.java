package inviteme.restfull.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import inviteme.restfull.entiity.Theme;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, String> {

}
