package lippo.hris.system.workflowengine.repository;

import lippo.hris.system.workflowengine.entity.WorkflowTrans;
import lippo.hris.system.workflowengine.response.WorkflowTransResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface WorkflowTransRepository extends JpaRepository<WorkflowTrans, Long> {

    @Query(nativeQuery = true, value = "SELECT trs.WmTransactionId AS id, trs.createdDate AS applyDate, doc.WmDocTypeName AS documentType, " +
            "sts.WmStatusDesc AS status, trs.WmTransCode AS transactionCode " +
            "FROM WMTransaction trs " +
            "INNER JOIN URMUser usr ON trs.UserId = usr.UserId " +
            "INNER JOIN WMDocType doc ON trs.WmDocTypeId = doc.WmDocTypeId " +
            "INNER JOIN WMStatus sts ON trs.WmStatusId = sts.WmStatusId " +
            "WHERE usr.UserName = :userName " +
            "AND (:docTypeCode IS NULL OR doc.WmDocTypeCode = :docTypeCode) " +
            "AND (:status IS NULL OR sts.WmStatusDesc = :status) " +
            "AND (:applyDate IS NULL OR trs.createdDate BETWEEN :applyDate AND DATEADD(DAY, 1, :applyDate)) " +
            "AND (:transactionCode IS NULL OR trs.WmTransCode LIKE '%'+:transactionCode+'%')")
    List<WorkflowTransResponse> getMyTransaction(@Param("userName") String userName,
                                                 @Param("docTypeCode") String docTypeCode,
                                                 @Param("status") String status,
                                                 @Param("applyDate") LocalDate applyDate,
                                                 @Param("transactionCode") String transactionCode);

    @Query(nativeQuery = true, value = "SELECT trs.WmTransactionId AS id, trs.createdDate AS applyDate, doc.WmDocTypeName AS documentType, " +
            "sts.WmStatusDesc AS status, trs.WmTransCode AS transactionCode " +
            "FROM WMTransaction trs " +
            "INNER JOIN URMUser usr ON trs.UserId = usr.UserId " +
            "INNER JOIN WMDocType doc ON trs.WmDocTypeId = doc.WmDocTypeId " +
            "INNER JOIN WMStatus sts ON trs.WmStatusId = sts.WmStatusId " +
            "WHERE usr.UserName = :userName " +
            "AND (:docTypeCode IS NULL OR doc.WmDocTypeCode = :docTypeCode) " +
            "AND (:status IS NULL OR sts.WmStatusDesc = :status) " +
            "AND (:applyDate IS NULL OR trs.createdDate BETWEEN :applyDate AND DATEADD(DAY, 1, :applyDate)) " +
            "AND (:transactionCode IS NULL OR trs.WmTransCode LIKE '%'+:transactionCode+'%')")
    List<WorkflowTransResponse> getApproverTransaction(@Param("userName") String userName,
                                                 @Param("docTypeCode") String docTypeCode,
                                                 @Param("status") String status,
                                                 @Param("applyDate") LocalDate applyDate,
                                                 @Param("transactionCode") String transactionCode);
}
