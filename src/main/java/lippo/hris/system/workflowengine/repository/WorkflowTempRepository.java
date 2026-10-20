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
            "WHERE (:groupTempId IS NULL OR dt.WmGroupTempId = :groupTempId)")
    List<WorkflowTemp> getWorkflowTempList(@Param("groupTempId") Long groupTempId);

    @Query(nativeQuery = true, value = "SELECT * FROM WMTemplate temp " +
            "WHERE WmTemplateActive = 1 " +
            "AND WmTemplateFgComp = :flagCompany " +
            "AND WmTemplateFgOrg = :flagOrganization " +
            "AND WmTemplateFgJobLvl = :flagJobLevel " +
            "AND WmTemplateFgJobTtl = :flagJobTitle " +
            "AND WmTemplateFgLoc = :flagLocation " +
            "AND WmTemplateFgPos = :flagPosition " +
            "AND WmTemplateFgEmp = :flagEmployee")
    List<WorkflowTemp> findByCriteriaAndActiveTrue(@Param("flagCompany") Boolean flagCompany,
                                           @Param("flagOrganization") Boolean flagOrganization,
                                           @Param("flagJobLevel") Boolean flagJobLevel,
                                           @Param("flagJobTitle") Boolean flagJobTitle,
                                           @Param("flagLocation") Boolean flagLocation,
                                           @Param("flagPosition") Boolean flagPosition,
                                           @Param("flagEmployee") Boolean flagEmployee);
}
