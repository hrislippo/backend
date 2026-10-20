package lippo.hris.system.workflowengine.repository;

import lippo.hris.system.workflowengine.entity.WorkflowGroupTemp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query(nativeQuery = true, value = "SELECT * FROM WmGroupTemp " +
            "WHERE WmDocTypeId = :docTypeId AND WmGroupTempSeq = :sequence")
    WorkflowGroupTemp findByDocTypeAndSequence(@Param("docTypeId") Long docTypeId, @Param("sequence") Integer sequence);

    @Query(nativeQuery = true, value = "SELECT gt.* FROM WmGroupTemp gt " +
            "INNER JOIN WmDocType dt ON gt.WmDocTypeId = dt.WmDocTypeId " +
            "WHERE (:codeSearch IS NULL OR gt.WmGroupTempCode LIKE '%'+:codeSearch+'%') " +
            "AND (:nameSearch IS NULL OR gt.WmGroupTempName LIKE '%'+:nameSearch+'%') " +
            "AND (:docTypeSearch IS NULL OR dt.WmDocTypeCode = :docTypeSearch) " +
            "AND (:activeSearch IS NULL OR gt.WmGroupTempActive = :activeSearch) " +
            "ORDER BY gt.WmDocTypeId, gt.WmGroupTempSeq",
            countQuery = "SELECT COUNT(1) FROM WmGroupTemp gt " +
                    "INNER JOIN WmDocType dt ON gt.WmDocTypeId = dt.WmDocTypeId " +
                    "WHERE (:codeSearch IS NULL OR gt.WmGroupTempCode LIKE '%'+:codeSearch+'%') " +
                    "AND (:nameSearch IS NULL OR gt.WmGroupTempName LIKE '%'+:nameSearch+'%') " +
                    "AND (:docTypeSearch IS NULL OR dt.WmDocTypeCode = :docTypeSearch) " +
                    "AND (:activeSearch IS NULL OR gt.WmGroupTempActive = :activeSearch)")
    Page<WorkflowGroupTemp> getGroupTemplate(@Param("codeSearch") String codeSearch,
                                             @Param("nameSearch") String nameSearch,
                                             @Param("docTypeSearch") String docTypeSearch,
                                             @Param("activeSearch") Boolean activeSearch,
                                             Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT * FROM WmGroupTemp " +
            "WHERE WmDocTypeId = :docTypeId " +
            "AND WmGroupTempFgComp = :flagCompany " +
            "AND WmGroupTempFgOrg = :flagOrganization " +
            "AND WmGroupTempFgJobLvl = :flagJobLevel " +
            "AND WmGroupTempFgJobTtl = :flagJobTitle " +
            "AND WmGroupTempFgLoc = :flagLocation " +
            "AND WmGroupTempFgPos = :flagPosition " +
            "AND WmGroupTempFgEmp = :flagEmployee " +
            "AND (:groupTempId IS NULL OR WmGroupTempId != :groupTempId)")
    WorkflowGroupTemp findByDocTypeAndCriteria(@Param("docTypeId") Long docTypeId,
                                               @Param("flagCompany") Boolean flagCompany,
                                               @Param("flagOrganization") Boolean flagOrganization,
                                               @Param("flagJobLevel") Boolean flagJobLevel,
                                               @Param("flagJobTitle") Boolean flagJobTitle,
                                               @Param("flagLocation") Boolean flagLocation,
                                               @Param("flagPosition") Boolean flagPosition,
                                               @Param("flagEmployee") Boolean flagEmployee,
                                               @Param("groupTempId") Long groupTempId);
}
