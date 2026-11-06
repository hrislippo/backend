package lippo.hris.system.organization.repository;

import lippo.hris.system.organization.entity.BusinessUnit;
import lippo.hris.system.organization.response.BusinessUnitResp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

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

    @Query(nativeQuery = true, value = "SELECT DISTINCT bu.* FROM RCMBusinessUnit bu " +
            "INNER JOIN RCMHRBP hrbp ON bu.RcmBsUnitId = hrbp.RcmBsUnitId " +
            "WHERE (NOT EXISTS (SELECT 1 FROM URMUserHRBP uh INNER JOIN URMUser u ON uh.UserId = u.UserId WHERE u.UserName = :nik) " +
            "OR hrbp.RcmHRBPId IN (SELECT uh.RcmHRBPId FROM URMUserHRBP uh WHERE uh.UserId = (SELECT UserId FROM URMUser WHERE UserName = :nik)))")
    List<BusinessUnit> findByHRBP(@Param("nik") String nik);
}
