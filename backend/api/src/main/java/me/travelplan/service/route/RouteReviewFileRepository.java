package me.travelplan.service.route;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RouteReviewFileRepository extends JpaRepository<RouteReviewFile, Long> {
    @Modifying
    @Query("delete from RouteReviewFile rf where rf.file.id in :ids")
    void deleteAllByFileIds(@Param("ids") List<Long> fileIdList);
}
