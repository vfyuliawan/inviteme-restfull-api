package inviteme.restfull.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import inviteme.restfull.entiity.Home;

@Repository
public interface HomeRepository extends JpaRepository<Home, String>{

}
