package lippo.hris.system.workflowengine.repository;

import lippo.hris.system.workflowengine.entity.WorkflowTrans;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkflowTransRepository extends JpaRepository<WorkflowTrans, Long> {
}
