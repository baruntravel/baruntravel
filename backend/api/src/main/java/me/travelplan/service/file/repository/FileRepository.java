package me.travelplan.service.file.repository;

import me.travelplan.service.file.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {

}
