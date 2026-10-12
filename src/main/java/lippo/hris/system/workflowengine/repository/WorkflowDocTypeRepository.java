package lippo.hris.system.workflowengine.repository;

import lippo.hris.system.workflowengine.entity.WorkflowDocType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkflowDocTypeRepository extends JpaRepository<WorkflowDocType, Long> {
    WorkflowDocType findByCode(String code);
}
