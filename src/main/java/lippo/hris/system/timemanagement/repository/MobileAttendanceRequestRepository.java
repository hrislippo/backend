package lippo.hris.system.timemanagement.repository;

import lippo.hris.system.timemanagement.entity.MobileAttendanceRequest;
import lippo.hris.system.timemanagement.response.MobileAttendanceResp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MobileAttendanceRequestRepository extends JpaRepository<MobileAttendanceRequest, Long> {

    @Query(nativeQuery = true,
            value = "SELECT ma.TmMobileAttendReqId AS id, " +
                    "ma.TmMobileAttendReqEmp AS empNIK, ma.TmMobileAttendReqTempCode AS tempCode, " +
                    "ma.TmMobileAttendReqStartDate AS startDate, ma.TmMobileAttendReqEndDate AS endDate " +
                    "FROM TMMobileAttendReq ma " +
                    "WHERE (:empNIK IS NULL OR ma.TmMobileAttendReqEmp LIKE '%'+:empNIK+'%') " +
                    "AND (:tempCode IS NULL OR ma.TmMobileAttendReqTempCode LIKE '%'+:tempCode+'%') " +
                    "AND (:startDate IS NULL OR ma.TmMobileAttendReqStartDate = :startDate) " +
                    "AND (:endDate IS NULL OR ma.TmMobileAttendReqEndDate = :endDate) " +
                    "ORDER BY ma.TmMobileAttendReqStartDate DESC",
            countQuery = "SELECT COUNT(1) " +
                    "FROM TMMobileAttendReq ma " +
                    "WHERE (:empNIK IS NULL OR ma.TmMobileAttendReqEmp LIKE '%'+:empNIK+'%') " +
                    "AND (:tempCode IS NULL OR ma.TmMobileAttendReqTempCode LIKE '%'+:tempCode+'%') " +
                    "AND (:startDate IS NULL OR ma.TmMobileAttendReqStartDate = :startDate) " +
                    "AND (:endDate IS NULL OR ma.TmMobileAttendReqEndDate = :endDate)")
    Page<MobileAttendanceResp> getMobileAttendance(@Param("empNIK") String empNIK,
                                                        @Param("tempCode") String tempCode,
                                                        @Param("startDate") LocalDate startDate,
                                                        @Param("endDate") LocalDate endDate,
                                                        Pageable pageable);

    @Query(nativeQuery = true,
            value = "SELECT * " +
                    "FROM TMMobileAttendReq ma " +
                    "WHERE ma.TmMobileAttendReqStartDate IS NOT NULL AND ma.TmMobileAttendReqEndDate IS NOT NULL " +
                    "AND ((CAST(GETDATE() AS DATE) >= ma.TmMobileAttendReqStartDate AND CAST(GETDATE() AS DATE) <= ma.TmMobileAttendReqEndDate AND ma.TmMobileAttendReqExist = 'false') " +
                    "OR (CAST(GETDATE() AS DATE) > ma.TmMobileAttendReqEndDate AND ma.TmMobileAttendReqExist = 'true'))")
    List<MobileAttendanceRequest> getMobileAttendanceDay();
}
