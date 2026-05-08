package nl.novi.eindopdrachtbackendapi.repositories;

import nl.novi.eindopdrachtbackendapi.entities.WatchHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchHistoryRepository extends JpaRepository<WatchHistoryEntity, Long> {
}
