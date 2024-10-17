package inviteme.restfull.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import inviteme.restfull.entiity.Descriptions;

@Repository
public interface DescriptionsRepository extends JpaRepository<Descriptions, String> {
 
            Page<Descriptions> OrderByCreatedAtDesc(Pageable pageable);
            Page<Descriptions> findByQuotesContainingOrderByCreatedAt(String quotes, Pageable pageable);

}
