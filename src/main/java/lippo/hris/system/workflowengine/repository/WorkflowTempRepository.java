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
            "WHERE WmGroupTempId = :groupTempId " +
            "AND (:codeSearch IS NULL OR WmTemplateCode LIKE '%'+:codeSearch+'%') " +
            "AND (:nameSearch IS NULL OR WmTemplateName LIKE '%'+:nameSearch+'%') " +
            "AND (:activeSearch IS NULL OR WmTemplateActive = :activeSearch)",
            countQuery = "SELECT COUNT(1) FROM WmTemplate " +
                    "WHERE WmGroupTempId = :groupTempId " +
                    "AND (:codeSearch IS NULL OR WmTemplateCode LIKE '%'+:codeSearch+'%') " +
                    "AND (:nameSearch IS NULL OR WmTemplateName LIKE '%'+:nameSearch+'%') " +
                    "AND (:activeSearch IS NULL OR WmTemplateActive = :activeSearch)")
    Page<WorkflowTemp> getTemplate(@Param("groupTempId") Long groupTempId,
                                   @Param("codeSearch") String codeSearch,
                                   @Param("nameSearch") String nameSearch,
                                   @Param("activeSearch") Boolean activeSearch,
                                   Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT temp.* FROM WMTemplate temp " +
            "WHERE (:groupTempId IS NULL OR temp.WmGroupTempId = :groupTempId)")
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


    @Query(nativeQuery = true, value = "SELECT DISTINCT TOP 1 tp.*, grp.WmGroupTempSeq " +
            "FROM WMGroupTemp grp " +
            "INNER JOIN WMDocType dt ON grp.WmDocTypeId = dt.WmDocTypeId " +
            "INNER JOIN WMTemplate tp ON gd.WmTemplateId = tp.WmTemplateId " +
            "INNER JOIN WMTempApprovee ape ON tp.WmTemplateId  = ape.WmTemplateId " +
            "WHERE grp.WMGroupTempActive = 1 AND tp.WMTemplateActive = 1 AND dt.WmDocTypeCode = :docTypeCode " +
            "AND (tp.WmTemplateFgComp = 0 OR ape.WmTempApproveeCompCode IN (:compCode)) " +
            "AND (tp.WmTemplateFgOrg = 0 OR ape.WmTempApproveeOrgCode IN (:orgCode)) " +
            "AND (tp.WmTemplateFgJobLvl = 0 OR ape.WmTempApproveeJobLvlCode IN (:jobLvlCode)) " +
            "AND (tp.WmTemplateFgJobTtl = 0 OR ape.WmTempApproveeJobTtlCode IN (:jobTtlCode)) " +
            "AND (tp.WmTemplateFgLoc = 0 OR ape.WmTempApproveeLocCode IN (:locCode)) " +
            "AND (tp.WmTemplateFgPos = 0 OR ape.WmTempApproveePosCode IN (:posCode)) " +
            "AND (tp.WmTemplateFgEmp = 0 OR ape.WmTempApproveeEmpNik IN (:empNik)) " +
            "ORDER BY grp.WmGroupTempSeq")
    WorkflowTemp findTemplateByApproveeCriteria(@Param("docTypeCode") String docTypeCode,
                                                      @Param("compCode") List<String> compCode,
                                                      @Param("orgCode") List<String> orgCode,
                                                      @Param("jobLvlCode") List<String> jobLvlCode,
                                                      @Param("jobTtlCode") List<String> jobTtlCode,
                                                      @Param("locCode") List<String> locCode,
                                                      @Param("posCode") List<String> posCode,
                                                      @Param("empNik") List<String> empNik);
}
