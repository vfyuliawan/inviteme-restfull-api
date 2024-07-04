package inviteme.restfull.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import inviteme.restfull.entiity.Gift;
import inviteme.restfull.entiity.Gifts;
import java.util.List;


public interface GiftsRepository extends JpaRepository<Gifts, String> {
    List<Gifts> findByGift(Gift gift);
}
