package lippo.hris.system.recruitment.repository;

import lippo.hris.system.recruitment.entity.RecruitmentActivity;
import lippo.hris.system.recruitment.response.RecruitmentActivityResp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecruitmentActivityRepository extends JpaRepository<RecruitmentActivity, Long> {

    @Query(nativeQuery = true,
            value="SELECT a.RcmActId AS id, a.RcmActGrp AS groupName, " +
                    "a.RcmActCode AS code, a.RcmActName AS name, a.RcmActFgInterview AS flagInterview, " +
                    "a.RcmActFgEmail AS flagEmail " +
                    "FROM RCMACTActivity a " +
                    "WHERE (:code IS NULL OR a.RcmActCode LIKE '%'+:code+'%') " +
                    "AND (:name IS NULL OR a.RcmActName LIKE '%'+:name+'%') " +
                    "AND (:group IS NULL OR a.RcmActGrp = :group ) " +
                    "ORDER BY a.RcmActCode",
            countQuery = "SELECT COUNT(1) " +
                    "FROM RCMACTActivity a " +
                    "WHERE (:code IS NULL OR a.RcmActCode LIKE '%'+:code+'%') " +
                    "AND (:name IS NULL OR a.RcmActName LIKE '%'+:name+'%') " +
                    "AND (:group IS NULL OR a.RcmActGrp = :group )")
    Page<RecruitmentActivityResp> getActivity(@Param("group") String group,
                                              @Param("code") String code,
                                              @Param("name") String name,
                                              Pageable pageable);

    @Query(nativeQuery = true,
            value = "SELECT DISTINCT ra.RcmActGrp " +
                    "FROM RCMEmpReqCandidate rc " +
                    "INNER JOIN RCMEmpReqCanActivity rca ON rc.EmpReqCanId = rca.EmpReqCanId " +
                    "INNER JOIN RCMACTActivity ra ON rca.RcmActId = ra.RcmActId " +
                    "WHERE rc.EmpReqCanId = :empReqCanId AND rca.EmpReqCanActStatus IN ('NOT_STARTED', 'IN_PROGRESS', 'FAILED')")
    List<String> findProgressByEmployeeReqCandidate (@Param("empReqCanId") Long empReqCanId);

    @Query(nativeQuery = true,
    value = "SELECT act.* " +
            "FROM RCMEmpRequest req " +
            "JOIN RCMACTActivityTemplate temp ON req.RcmTempId = temp.RcmTempId " +
            "JOIN RCMACTActivity act ON temp.RcmActId = act.RcmActId " +
            "WHERE req.EmpReqId = :id")
    List<RecruitmentActivity> getEmployeeRequestActivitySchedule(@Param("id") Long id);
}
