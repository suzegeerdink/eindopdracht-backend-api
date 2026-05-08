package nl.novi.eindopdrachtbackendapi.repositories;

import nl.novi.eindopdrachtbackendapi.entities.FilmEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FilmRepository extends JpaRepository<FilmEntity, Long> {
    Optional<FilmEntity> findByTitle(String title);
}
