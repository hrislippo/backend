package lippo.hris.system.workflowengine.repository;

import lippo.hris.system.workflowengine.entity.WorkflowTemp;
import lippo.hris.system.workflowengine.entity.WorkflowTempApprover;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorkflowTempApproverRepository extends JpaRepository<WorkflowTempApprover, Long> {

    List<WorkflowTempApprover> findByWorkflowTemp(WorkflowTemp workflowTemp);
    List<WorkflowTempApprover> findByLevelAndWorkflowTemp(Integer level, WorkflowTemp workflowTemp);

    @Query(nativeQuery = true, value="SELECT MAX(WmTempApproverLvl) FROM WMTempApprover " +
            "WHERE WmTemplateId = :tempId")
    Integer findMaxLevel(@Param("tempId") Long tempId);

    @Query(nativeQuery = true, value="SELECT * FROM WMTempApprover " +
            "WHERE WmTemplateId = :tempId AND WmTempApproverLvl = :level " +
            "AND (:compCode IS NULL OR WmTempApproverCompCode = :compCode) AND (:orgCode IS NULL OR WmTempApproverOrgCode = :orgCode) " +
            "AND (:jobLvlCode IS NULL OR WmTempApproverJobLvlCode = :jobLvlCode) AND (:jobTtlCode IS NULL OR WmTempApproverJobTtlCode = :jobTtlCode) " +
            "AND (:locCode IS NULL OR WmTempApproverLocCode = :locCode) AND (:posCode IS NULL OR WmTempApproverPosCode = :posCode) " +
            "AND (:empNik IS NULL OR WmTempApproverEmpNik = :empNik) AND (:orgChartCode IS NULL OR WmTempApproverOrgChartCode = :orgChartCode)")
    WorkflowTempApprover findByWorkflowTempLevelAndCriteria (@Param("tempId") Long tempId, @Param("level") Integer level,
                                                             @Param("compCode") String compCode, @Param("orgCode") String orgCode,
                                                             @Param("jobLvlCode") String jobLvlCode, @Param("jobTtlCode") String jobTtlCode,
                                                             @Param("locCode") String locCode, @Param("posCode") String posCode,
                                                             @Param("empNik") String empNik, @Param("orgChartCode") String orgChartCode);
}
