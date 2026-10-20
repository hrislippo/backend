package lippo.hris.system.workflowengine.repository;

import lippo.hris.system.workflowengine.entity.WorkflowApproverTemp;
import lippo.hris.system.workflowengine.entity.WorkflowTemp;
import lippo.hris.system.workflowengine.entity.WorkflowTempApprover;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorkflowTempApproverRepository extends JpaRepository<WorkflowTempApprover, Long> {

    List<WorkflowTempApprover> findByWorkflowTemp(WorkflowTemp workflowTemp);

    @Query(nativeQuery = true, value="SELECT MAX(WmTempApproverLvl) FROM WMTempApprover " +
            "WHERE WmTemplateId = :tempId")
    Integer findMaxLevel(@Param("tempId") Long tempId);
}
