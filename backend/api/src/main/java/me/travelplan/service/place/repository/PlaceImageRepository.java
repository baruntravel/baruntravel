package me.travelplan.service.place.repository;

import me.travelplan.service.place.domain.PlaceImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceImageRepository extends JpaRepository<PlaceImage, Long> {
}
