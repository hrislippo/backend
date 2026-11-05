package lippo.hris.system.timemanagement.repository;

import lippo.hris.system.timemanagement.entity.MobileAttendanceGroupTemplate;
import lippo.hris.system.timemanagement.response.MobileAttendanceGroupTempResp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface MobileAttendanceGroupTemplateRepository extends JpaRepository<MobileAttendanceGroupTemplate, Long> {

    @Query(nativeQuery = true, value="SELECT temp.* " +
            "FROM TMMobileAttendGrpTemp temp " +
            "WHERE (:code IS NULL OR temp.MobileAttendGrpTempCode = :code) " +
            "AND (:id IS NULL OR temp.MobileAttendGrpTempId != :id)")
    MobileAttendanceGroupTemplate findExistingCode(@Param("code") String code,
                                                       @Param("id") Long id);

    @Query(nativeQuery = true, value="SELECT temp.* " +
            "FROM TMMobileAttendGrpTemp temp " +
            "WHERE (:name IS NULL OR temp.MobileAttendGrpTempName = :name) " +
            "AND (:id IS NULL OR temp.MobileAttendGrpTempId != :id)")
    MobileAttendanceGroupTemplate findExistingName(@Param("name") String name,
                                                   @Param("id") Long id);

    @Query(nativeQuery = true, value = "SELECT temp.MobileAttendGrpTempId AS id, temp.MobileAttendGrpTempCode AS code, " +
            "temp.MobileAttendGrpTempName AS name, bu.RcmBsUnitName AS businessUnit, hrbp.RcmHRBPName AS hrbp " +
            "FROM TMMobileAttendGrpTemp temp " +
            "INNER JOIN RCMHRBP hrbp ON temp.RcmHRBPId = hrbp.RcmHRBPId " +
            "INNER JOIN RCMBusinessUnit bu ON hrbp.RcmBsUnitId = bu.RcmBsUnitId " +
            "WHERE (:code IS NULL OR temp.MobileAttendGrpTempCode LIKE '%'+:code+'%') " +
            "AND (:name IS NULL OR temp.MobileAttendGrpTempName LIKE '%'+:name+'%') " +
            "AND (:buName IS NULL OR bu.RcmBsUnitName LIKE '%'+:buName+'%') " +
            "AND (:hrbpName IS NULL OR hrbp.RcmHRBPName LIKE '%'+:hrbpName+'%')")
    List<MobileAttendanceGroupTempResp> getMobileAttendanceGroupTemplate(@Param("code") String code,
                                                                         @Param("name") String name,
                                                                         @Param("buName") String buName,
                                                                         @Param("hrbpName") String hrbpName);
}
