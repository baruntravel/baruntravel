package me.travelplan.service.file;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {
    // @Modifying을 사용하는 경우 clearAutomatically=true로 설정하여 영속성 컨텍스트를 초기화해야하지만
    // service layer에서 변경감지 사용과 벌크성 삭제 쿼리를 하고 findBy로 다시 조회하는 것이 없기 때문에
    // 설정추가 X
    @Modifying
    @Query("delete from File f where f.id in :ids")
    void deleteAllByIds(@Param("ids") List<Long> fileIdList);
}
