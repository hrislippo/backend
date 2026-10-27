package lippo.hris.system.workflowengine.repository;

import lippo.hris.system.workflowengine.entity.WorkflowTransApprover;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WorkflowTransApproverRepository extends JpaRepository<WorkflowTransApprover, Long> {

}
