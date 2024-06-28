package inviteme.restfull.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import inviteme.restfull.entiity.User;



@Repository
public interface UserRepository extends JpaRepository<User , String> {
    Optional<User> findFirstByToken(String token);

    Optional<User> findUserByToken(String token);

    Optional<User> findUserByUsername(String username);

    Page<User> findByUsernameContaining(String username, Pageable pageable);

}
