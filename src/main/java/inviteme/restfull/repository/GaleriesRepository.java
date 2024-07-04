package inviteme.restfull.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import inviteme.restfull.entiity.Galeries;
import inviteme.restfull.entiity.Galery;

import java.util.List;


public interface GaleriesRepository extends JpaRepository<Galeries, String>{
    List<Galeries> findByGalery(Galery galery);
}
