package lippo.hris.system.workflowengine.repository;

import lippo.hris.system.workflowengine.entity.WorkflowTemp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorkflowTempRepository extends JpaRepository<WorkflowTemp, Long> {

    WorkflowTemp findByCode(String code);
    WorkflowTemp findByName(String name);
    List<WorkflowTemp> findAllByActiveTrue();

    @Query(nativeQuery = true, value = "SELECT * FROM WmTemplate " +
            "WHERE (:codeSearch IS NULL OR WmTemplateCode LIKE '%'+:codeSearch+'%') " +
            "AND (:nameSearch IS NULL OR WmTemplateName LIKE '%'+:nameSearch+'%') " +
            "AND (:activeSearch IS NULL OR WmTemplateActive = :activeSearch)",
            countQuery = "SELECT COUNT(1) FROM WmTemplate " +
                    "WHERE (:codeSearch IS NULL OR WmTemplateCode LIKE '%'+:codeSearch+'%') " +
                    "AND (:nameSearch IS NULL OR WmTemplateName LIKE '%'+:nameSearch+'%') " +
                    "AND (:activeSearch IS NULL OR WmTemplateActive = :activeSearch)")
    Page<WorkflowTemp> getTemplate(@Param("codeSearch") String codeSearch,
                                   @Param("nameSearch") String nameSearch,
                                   @Param("activeSearch") Boolean activeSearch,
                                   Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT temp.* FROM WMGroupTempDt dt " +
            "INNER JOIN WMTemplate temp ON dt.WmTemplateId = temp.WmTemplateId " +
            "WHERE (:groupTempId IS NULL OR dt.WmGroupTempId = :groupTempId) " +
            "ORDER BY dt.WmGroupTempDtSeq")
    List<WorkflowTemp> getWorkflowTempList(@Param("groupTempId") Long groupTempId);
}
