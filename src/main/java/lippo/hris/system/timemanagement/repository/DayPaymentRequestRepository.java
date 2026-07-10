package lippo.hris.system.timemanagement.repository;

import lippo.hris.system.recruitment.response.BusinessUnitResp;
import lippo.hris.system.timemanagement.entity.DayPaymentRequest;
import lippo.hris.system.timemanagement.response.DayPaymentResp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface DayPaymentRequestRepository extends JpaRepository<DayPaymentRequest, Long> {

    @Query(nativeQuery = true,
            value = "SELECT dp.TmDayPaymentReqId AS id, dp.TmDayPaymentReqEmp AS empNIK, dp.TmDayPaymentReqDate AS startDate, " +
                    "dp.TmDayPaymentReqExpDate AS expiryDate, dp.TmDayPaymentReqCount AS dpCount " +
                    "FROM TMDayPaymentReq dp " +
                    "WHERE (:empNIK IS NULL OR dp.TmDayPaymentReqEmp LIKE '%'+:empNIK+'%') " +
                    "AND (:startDate IS NULL OR dp.TmDayPaymentReqDate = :startDate) " +
                    "AND (:expiryDate IS NULL OR dp.TmDayPaymentReqExpDate = :expiryDate) " +
                    "ORDER BY dp.TmDayPaymentReqDate DESC",
            countQuery = "SELECT COUNT(1) " +
                    "FROM TMDayPaymentReq dp " +
                    "WHERE (:empNIK IS NULL OR dp.TmDayPaymentReqEmp LIKE '%'+:empNIK+'%') " +
                    "AND (:startDate IS NULL OR dp.TmDayPaymentReqDate = :startDate) " +
                    "AND (:expiryDate IS NULL OR dp.TmDayPaymentReqExpDate = :expiryDate)")
    Page<DayPaymentResp> getDayPayment(@Param("empNIK") String empNIK,
                                        @Param("startDate") LocalDate startDate,
                                        @Param("expiryDate") LocalDate expiryDate,
                                        Pageable pageable);
}
