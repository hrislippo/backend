package lippo.hris.system.recruitment.repository;

import lippo.hris.system.recruitment.entity.CandidatePool;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CandidatePoolRepository extends JpaRepository<CandidatePool, Long> {

    @Query(nativeQuery = true,
            value = "SELECT can.* FROM RCMCanPool can " +
                    "WHERE (:code IS NULL OR can.RcmCanPoolCode LIKE '%'+:code+'%') " +
                    "AND (:name IS NULL OR can.RcmCanPoolName LIKE '%'+:name+'%') " +
                    "ORDER BY can.RcmCanPoolName",
            countQuery = "SELECT COUNT(1) FROM RCMCanPool can " +
                    "WHERE (:code IS NULL OR can.RcmCanPoolCode LIKE '%'+:code+'%') " +
                    "AND (:name IS NULL OR can.RcmCanPoolName LIKE '%'+:name+'%') " +
                    "ORDER BY can.RcmCanPoolName")
    Page<CandidatePool> findByCodeAndName(@Param("code") String code,
                                          @Param("name") String name,
                                          Pageable pageable);
}
