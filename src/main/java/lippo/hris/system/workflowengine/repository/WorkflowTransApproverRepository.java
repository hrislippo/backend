package lippo.hris.system.workflowengine.repository;

import lippo.hris.system.workflowengine.entity.WorkflowTrans;
import lippo.hris.system.workflowengine.entity.WorkflowTransApprover;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorkflowTransApproverRepository extends JpaRepository<WorkflowTransApprover, Long> {

    List<WorkflowTransApprover> findByLevelAndWorkflowTrans(Integer level, WorkflowTrans workflowTrans);

    @Query(nativeQuery = true, value="SELECT MAX(WmTransApproverLvl) FROM WMTransApprover " +
            "WHERE WmTransactionId = :transId")
    Integer findMaxLevel(@Param("transId") Long transId);
}
