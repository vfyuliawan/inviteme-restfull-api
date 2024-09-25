package inviteme.restfull.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import inviteme.restfull.entiity.ThemeExample;


public interface ThemeExampleRepository extends JpaRepository<ThemeExample, String> {

    Page<ThemeExample> findByNameContaining(String name, Pageable pageable);

    Page<ThemeExample> findAllByOrderByCreatedAtDesc(Pageable pageable);


}
