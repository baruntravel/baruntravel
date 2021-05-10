package me.travelplan.service.place.repository;

import me.travelplan.service.place.domain.PlaceCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceCategoryRepository extends JpaRepository<PlaceCategory, String> {
}
