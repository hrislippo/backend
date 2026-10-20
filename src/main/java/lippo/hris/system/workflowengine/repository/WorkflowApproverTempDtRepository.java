package lippo.hris.system.workflowengine.repository;

import lippo.hris.system.workflowengine.entity.WorkflowApproverTemp;
import lippo.hris.system.workflowengine.entity.WorkflowApproverTempDt;
import lippo.hris.system.workflowengine.entity.WorkflowTempApprovee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorkflowApproverTempDtRepository extends JpaRepository<WorkflowApproverTempDt, Long> {
    List<WorkflowApproverTempDt> findByWorkflowApproverTemp(WorkflowApproverTemp workflowApproverTemp);

    @Query(nativeQuery = true, value = "SELECT * FROM WMApproverTempDt WHERE WmApproverTempId = :approverTempId " +
            "AND (:orgCode IS NULL OR WmApproverTempDtOrgCode = :orgCode) AND (:jobLvlCode IS NULL OR WmApproverTempDtJobLvlCode = :jobLvlCode) " +
            "AND (:compCode IS NULL OR WmApproverTempDtCompCode = :compCode) AND (:locCode IS NULL OR WmApproverTempDtLocCode = :locCode) " +
            "AND (:posCode IS NULL OR WmApproverTempDtPosCode = :posCode) AND (:jobTtlCode IS NULL OR WmApproverTempDtJobTtlCode = :jobTtlCode) " +
            "AND (:empNik IS NULL OR WmApproverTempDtEmpNik = :empNik)")
    WorkflowApproverTempDt findByWorkflowApproverTempAndCriteria(@Param("approverTempId") Long approverTempId,
                                                       @Param("compCode") String compCode,
                                                       @Param("orgCode") String orgCode,
                                                       @Param("jobLvlCode") String jobLvlCode,
                                                       @Param("jobTtlCode") String jobTtlCode,
                                                       @Param("locCode") String locCode,
                                                       @Param("posCode") String posCode,
                                                       @Param("empNik") String empNik);
}
