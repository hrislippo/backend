package lippo.hris.system.workflowengine.repository;

import lippo.hris.system.workflowengine.entity.WorkflowGroupTemp;
import lippo.hris.system.workflowengine.entity.WorkflowGroupTempDt;
import lippo.hris.system.workflowengine.entity.WorkflowTemp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorkflowGroupTempDtRepository extends JpaRepository<WorkflowGroupTempDt, Long> {

    WorkflowGroupTempDt findByWorkflowGroupTempAndWorkflowTemp(WorkflowGroupTemp workflowGroupTemp, WorkflowTemp workflowTemp);

    @Query(nativeQuery = true, value = "SELECT * FROM WMGroupTempDt " +
            "WHERE WmGroupTempId = :groupTempId " +
            "ORDER BY WmGroupTempDtSeq")
    List<WorkflowGroupTempDt> findByWorkflowGroupTemp(@Param("groupTempId") Long groupTempId);
}
