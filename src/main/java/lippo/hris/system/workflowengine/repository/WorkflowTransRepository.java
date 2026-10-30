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

    @Query(nativeQuery = true, value = "SELECT DISTINCT trs.WmTransactionId AS id, apr.WmTransApproverId AS approverId, trs.createdDate AS applyDate, " +
            "wt.WmDocTypeName AS documentType, st.WmStatusDesc AS status, trs.WmTransCode AS transactionCode " +
            "FROM WMTransaction trs INNER JOIN WMTransApprover apr ON trs.WmTransactionId = apr.WmTransactionId " +
            "INNER JOIN URMUser usr ON apr.UserId = usr.UserId INNER JOIN WMStatus st ON trs.WmStatusId = st.WmStatusId " +
            "INNER JOIN WMDocType wt ON trs.WmDocTypeId = wt.WmDocTypeId " +
            "WHERE usr.UserName = :empNik AND apr.WmTransApproverFgApprove IS NULL AND st.WmStatusDesc IN ('OPEN', 'IN PROGRESS') " +
            "AND NOT EXISTS (SELECT 1 FROM WMTransApprover currentLevel WHERE currentLevel.WmTransactionId = apr.WMTransactionId " +
            "AND currentLevel.WmTransApproverLvl = apr.WmTransApproverLvl AND currentLevel.WmTransApproverFgApprove = 1) " +
            "AND NOT EXISTS (SELECT 1 FROM WMTransApprover previousLevel WHERE previousLevel.WmTransactionId = apr.WmTransactionId " +
            "AND previousLevel.WmTransApproverLvl < apr.WmTransApproverLvl " +
            "AND NOT EXISTS (SELECT 1 FROM WMTransApprover previousApproved " +
            "WHERE previousApproved.WmTransactionId = previousLevel.WmTransactionId " +
            "AND previousApproved.WmTransApproverLvl = previousLevel.WmTransApproverLvl " +
            "AND previousApproved.WmTransApproverFgApprove = 1))")
    List<WorkflowTransResponse> getApproverTransaction(@Param("empNik") String empNik);
}
