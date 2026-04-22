package lippo.hris.system.recruitment.repository;

import lippo.hris.system.recruitment.entity.BusinessUnit;
import lippo.hris.system.recruitment.entity.HRBP;
import lippo.hris.system.recruitment.response.BusinessUnitResp;
import lippo.hris.system.recruitment.response.HRBPResp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HRBPRepository extends JpaRepository<HRBP, Long> {
    List<HRBP> findByBusinessUnit(BusinessUnit businessUnit);

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
