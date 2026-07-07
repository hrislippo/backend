package lippo.hris.system.recruitment.repository;

import lippo.hris.system.recruitment.entity.PositionLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PositionLevelRepository extends JpaRepository<PositionLevel, Long> {

    @Query(nativeQuery = true,
            value = "SELECT p.* " +
                    "FROM RCMPosLevel p " +
                    "WHERE (:code IS NULL OR p.RcmPosLevelCode LIKE '%'+:code+'%') " +
                    "AND (:name IS NULL OR p.RcmPosLevelName LIKE '%'+:name+'%')",
            countQuery = "SELECT COUNT(1) " +
                    "FROM RCMPosLevel p " +
                    "WHERE (:code IS NULL OR p.RcmPosLevelCode LIKE '%'+:code+'%') " +
                    "AND (:name IS NULL OR p.RcmPosLevelName LIKE '%'+:name+'%')")
    Page<PositionLevel> getPositionLevel(@Param("code") String code,
                                           @Param("name") String name,
                                           Pageable pageable);
}
