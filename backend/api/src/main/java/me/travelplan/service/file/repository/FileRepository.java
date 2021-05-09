package me.travelplan.service.file.repository;

import me.travelplan.service.file.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {
    @Modifying
    @Query("delete from File f where f.id in :ids")
    void deleteAllByIds(@Param("ids") List<Long> fileIdList);
}
