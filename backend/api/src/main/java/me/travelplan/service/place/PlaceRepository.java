package me.travelplan.service.place;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    @Query("select p from Place p join fetch p.category where p.id=:id")
    Optional<Place> findByIdWithCategory(@Param("id") Long id);
}
