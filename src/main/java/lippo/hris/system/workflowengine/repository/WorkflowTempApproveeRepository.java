package lippo.hris.system.workflowengine.repository;

import lippo.hris.system.workflowengine.entity.WorkflowTemp;
import lippo.hris.system.workflowengine.entity.WorkflowTempApprovee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorkflowTempApproveeRepository extends JpaRepository<WorkflowTempApprovee, Long> {

    List<WorkflowTempApprovee> findByWorkflowTemp(WorkflowTemp workflowTemp);

    @Query(nativeQuery = true, value = "SELECT * FROM WMTempApprovee WHERE WmTemplateId = :tempId " +
            "AND (:orgCode IS NULL OR WmTempApproveeOrgCode = :orgCode) AND (:jobLvlCode IS NULL OR WmTempApproveeJobLvlCode = :jobLvlCode) " +
            "AND (:compCode IS NULL OR WmTempApproveeCompCode = :compCode) AND (:locCode IS NULL OR WmTempApproveeLocCode = :locCode) " +
            "AND (:posCode IS NULL OR WmTempApproveePosCode = :posCode) AND (:jobTtlCode IS NULL OR WmTempApproveeJobTtlCode = :jobTtlCode) " +
            "AND (:empNik IS NULL OR WmTempApproveeEmpNik = :empNik)")
    WorkflowTempApprovee findByWorkflowTempAndCriteria(@Param("tempId") Long tempId,
                                                       @Param("compCode") String compCode,
                                                       @Param("orgCode") String orgCode,
                                                       @Param("jobLvlCode") String jobLvlCode,
                                                       @Param("jobTtlCode") String jobTtlCode,
                                                       @Param("locCode") String locCode,
                                                       @Param("posCode") String posCode,
                                                       @Param("empNik") String empNik);

    @Query(nativeQuery = true, value = "SELECT tape.* FROM WMTemplate temp " +
            "INNER JOIN WMTempApprovee tape ON temp.WmTemplateId = tape.WmTemplateId " +
            "WHERE temp.WmGroupTempId = :groupTempId " +
            "AND (:tempId IS NULL OR tape.WmTemplateId != :tempId) " +
            "AND (:compCode IS NULL OR tape.WmTempApproveeCompCode = :compCode) " +
            "AND (:orgCode IS NULL OR tape.WmTempApproveeOrgCode = :orgCode) " +
            "AND (:jobLvlCode IS NULL OR tape.WmTempApproveeJobLvlCode = :jobLvlCode) " +
            "AND (:jobTtlCode IS NULL OR tape.WmTempApproveeJobTtlCode = :jobTtlCode) " +
            "AND (:posCode IS NULL OR tape.WmTempApproveePosCode = :posCode) " +
            "AND (:locCode IS NULL OR tape.WmTempApproveeLocCode = :locCode) " +
            "AND (:empNik IS NULL OR tape.WmTempApproveeEmpNik = :empNik)")
    WorkflowTempApprovee findByWorkflowGroupTempAndCriteria(@Param("groupTempId") Long groupTempId,
                                                            @Param("tempId") Long tempId,
                                                            @Param("compCode") String compCode,
                                                            @Param("orgCode") String orgCode,
                                                            @Param("jobLvlCode") String jobLvlCode,
                                                            @Param("jobTtlCode") String jobTtlCode,
                                                            @Param("locCode") String locCode,
                                                            @Param("posCode") String posCode,
                                                            @Param("empNik") String empNik);
}
