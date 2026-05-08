package nl.novi.eindopdrachtbackendapi.repositories;

import nl.novi.eindopdrachtbackendapi.entities.SeriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeriesRepository extends JpaRepository<SeriesEntity, Long> {
    Optional<SeriesEntity> findByTitle(String title);
}
