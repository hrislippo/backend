package lippo.hris.system.workflowengine.repository;

import lippo.hris.system.workflowengine.entity.WorkflowStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkflowStatusRepository extends JpaRepository<WorkflowStatus, Long> {
    WorkflowStatus findByDescription(String description);
}
