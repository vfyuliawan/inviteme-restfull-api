package inviteme.restfull.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import inviteme.restfull.entiity.Hero;

public interface HeroRepository extends JpaRepository<Hero, String> {

}
