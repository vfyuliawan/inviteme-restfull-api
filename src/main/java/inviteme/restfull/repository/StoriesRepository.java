package inviteme.restfull.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import inviteme.restfull.entiity.Stories;
import inviteme.restfull.entiity.Story;
import java.util.*;

public interface StoriesRepository extends JpaRepository<Stories, String> {
    List<Stories> findByStory(Story story);
}
