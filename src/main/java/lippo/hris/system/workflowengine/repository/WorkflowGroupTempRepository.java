package lippo.hris.system.workflowengine.repository;

import lippo.hris.system.workflowengine.entity.WorkflowGroupTemp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WorkflowGroupTempRepository extends JpaRepository<WorkflowGroupTemp, Long> {
    WorkflowGroupTemp findByCode(String code);
    WorkflowGroupTemp findByName(String name);

    @Query(nativeQuery = true, value = "SELECT TOP 1 * FROM WmGroupTemp " +
            "WHERE WmDocTypeId = :docTypeId " +
            "ORDER BY WmGroupTempSeq DESC")
    WorkflowGroupTemp latestByDocType(@Param("docTypeId") Long docTypeId);
}
