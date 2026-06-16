package nl.novi.eindopdrachtbackendapi.repositories;

import nl.novi.eindopdrachtbackendapi.entities.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<LoanEntity, Long> {
    List<LoanEntity> findAllByProfileId(Long profileId);
}
