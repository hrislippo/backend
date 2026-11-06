package lippo.hris.system.organization.repository;

import lippo.hris.system.organization.entity.BusinessUnit;
import lippo.hris.system.organization.entity.HRBP;
import lippo.hris.system.organization.response.HRBPResp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HRBPRepository extends JpaRepository<HRBP, Long> {
    HRBP findByCode(String code);

    @Query(nativeQuery = true, value = "SELECT * FROM RCMHRBP WHERE RcmBsUnitId = :businessUnitId " +
            "AND (NOT EXISTS (SELECT 1 FROM URMUserHRBP uh INNER JOIN URMUser u ON uh.UserId = u.UserId WHERE u.UserName = :nik) " +
            "OR RcmHRBPId IN (SELECT uh.RcmHRBPId FROM URMUserHRBP uh WHERE uh.UserId = (SELECT UserId FROM URMUser WHERE UserName = :nik)))")
    List<HRBP> findByBusinessUnit(@Param("businessUnitId") Long businessUnitId,
                                  @Param("nik") String nik);

    @Query(nativeQuery = true,
            value = "SELECT hrbp.RcmHRBPId AS id, bu.RcmBsUnitName AS businessUnitName, " +
                    "hrbp.RcmHRBPCode AS code, hrbp.RcmHRBPName AS name " +
                    "FROM RCMBusinessUnit bu " +
                    "INNER JOIN RCMHRBP hrbp ON bu.RcmBsUnitId = hrbp.RcmBsUnitId " +
                    "WHERE (:buName IS NULL OR bu.RcmBsUnitName LIKE '%'+:buName+'%') " +
                    "AND (:code IS NULL OR hrbp.RcmHRBPCode LIKE '%'+:code+'%') " +
                    "AND (:name IS NULL OR hrbp.RcmHRBPName LIKE '%'+:name+'%') " +
                    "ORDER BY hrbp.RcmHRBPName",
            countQuery = "SELECT COUNT(1) " +
                    "FROM RCMBusinessUnit bu " +
                    "INNER JOIN RCMHRBP hrbp ON bu.RcmBsUnitId = hrbp.RcmBsUnitId " +
                    "WHERE (:buName IS NULL OR bu.RcmBsUnitName LIKE '%'+:buName+'%') " +
                    "AND (:code IS NULL OR hrbp.RcmHRBPCode LIKE '%'+:code+'%') " +
                    "AND (:name IS NULL OR hrbp.RcmHRBPName LIKE '%'+:name+'%')")
    Page<HRBPResp> findByCodeAndName(@Param("buName") String buName,
                                     @Param("code") String code,
                                     @Param("name") String name,
                                     Pageable pageable);
}
