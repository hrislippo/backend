package lippo.hris.system.recruitment.repository;

import lippo.hris.system.recruitment.entity.BusinessUnit;
import lippo.hris.system.recruitment.response.BusinessUnitResp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BusinessUnitRepository extends JpaRepository<BusinessUnit, Long> {

    @Query(nativeQuery = true,
            value = "SELECT bu.RcmBsUnitId AS id, bu.RcmBsUnitCode AS code, bu.RcmBsUnitName AS name " +
                    "FROM RCMBusinessUnit bu " +
                    "WHERE (:code IS NULL OR bu.RcmBsUnitCode LIKE '%'+:code+'%') " +
                    "AND (:name IS NULL OR bu.RcmBsUnitName LIKE '%'+:name+'%') " +
                    "ORDER BY bu.RcmBsUnitName",
            countQuery = "SELECT COUNT(1) " +
                    "FROM RCMBusinessUnit bu " +
                    "WHERE (:code IS NULL OR bu.RcmBsUnitCode LIKE '%'+:code+'%') " +
                    "AND (:name IS NULL OR bu.RcmBsUnitName LIKE '%'+:name+'%')")
    Page<BusinessUnitResp> findByCodeAndName(@Param("code") String code,
                                             @Param("name") String name,
                                             Pageable pageable);
}
