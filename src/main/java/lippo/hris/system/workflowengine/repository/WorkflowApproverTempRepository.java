package lippo.hris.system.workflowengine.repository;

import lippo.hris.system.workflowengine.entity.WorkflowApproverTemp;
import lippo.hris.system.workflowengine.entity.WorkflowTemp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorkflowApproverTempRepository extends JpaRepository<WorkflowApproverTemp, Long> {

    WorkflowApproverTemp findByCode(String code);
    WorkflowApproverTemp findByName(String name);

    @Query(nativeQuery = true, value = "SELECT * FROM WmApproverTemp " +
            "WHERE (:codeSearch IS NULL OR WmApproverTempCode LIKE '%'+:codeSearch+'%') " +
            "AND (:nameSearch IS NULL OR WmApproverTempName LIKE '%'+:nameSearch+'%') " +
            "AND (:activeSearch IS NULL OR WmApproverTempActive = :activeSearch)",
            countQuery = "SELECT COUNT(1) FROM WmApproverTemp " +
                    "WHERE (:codeSearch IS NULL OR WmApproverTempCode LIKE '%'+:codeSearch+'%') " +
                    "AND (:nameSearch IS NULL OR WmApproverTempName LIKE '%'+:nameSearch+'%') " +
                    "AND (:activeSearch IS NULL OR WmApproverTempActive = :activeSearch)")
    Page<WorkflowApproverTemp> getApproverTemplate(@Param("codeSearch") String codeSearch,
                                   @Param("nameSearch") String nameSearch,
                                   @Param("activeSearch") Boolean activeSearch,
                                   Pageable pageable);

    @Query(nativeQuery = true, value="SELECT app.* FROM WMTempApprover temp " +
            "INNER JOIN WMApproverTemp app ON temp.WmApproverTempId = app.WmApproverTempId " +
            "WHERE temp.WmTemplateId = :tempId AND temp.WmTempApproverLvl = :level " +
            "ORDER BY WmTempApproverSeq")
    List<WorkflowApproverTemp> findByTemplateAndLevel(@Param("tempId") Long tempId, @Param("level") Integer level);
}
