package lippo.hris.system.recruitment.repository;

import lippo.hris.system.recruitment.entity.EmployeeRequestCandidate;
import lippo.hris.system.recruitment.entity.EmployeeRequestCandidateActivity;
import lippo.hris.system.recruitment.entity.EmployeeRequestForm;
import lippo.hris.system.recruitment.entity.RecruitmentActivity;
import lippo.hris.system.recruitment.response.DashboardPipelineResp;
import lippo.hris.system.recruitment.response.EmployeeRequestCandidateResp;
import lippo.hris.system.recruitment.response.EmployeeRequestKanban;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRequestCandidateActivityRepository extends JpaRepository<EmployeeRequestCandidateActivity, Long> {

    List<EmployeeRequestCandidateActivity> findByEmployeeRequestCandidate (EmployeeRequestCandidate employeeRequestCandidate);
    EmployeeRequestCandidateActivity findByEmployeeRequestCandidateAndRecruitmentActivity (EmployeeRequestCandidate employeeRequestCandidate, RecruitmentActivity recruitmentActivity);
    List<EmployeeRequestCandidateActivity> findByEmployeeRequestCandidateAndStatus (EmployeeRequestCandidate employeeRequestCandidate, String status);
    void deleteAllByEmployeeRequestCandidate (EmployeeRequestCandidate employeeRequestCandidate);

    @Query(nativeQuery = true,
            value = "WITH StageOrder AS ( " +
                    "    SELECT * FROM (VALUES " +
                    "    (1, 'Assessment'), (2, 'Offering'), (3, 'Background Check'), (4, 'Sign Agreement'), (5, 'Onboarding') " +
                    "    ) v(StageOrder, StageName) " +
                    "), " +
                    "StageAgg AS ( " +
                    "    SELECT " +
                    "        ca.EmpReqCanId, " +
                    "        so.StageOrder, " +
                    "        so.StageName, " +
                    "        MAX(CASE WHEN ca.EmpReqCanActStatus = 'IN_PROGRESS' THEN 1 ELSE 0 END) AS HasInProgress, " +
                    "        MAX(CASE WHEN ca.EmpReqCanActStatus <> 'COMPLETED' OR ca.EmpReqCanActStatus IS NULL THEN 1 ELSE 0 END) AS HasNotCompleted " +
                    "    FROM RCMEmpReqCanActivity ca " +
                    "    LEFT JOIN RCMEmpReqCandidate rc ON ca.EmpReqCanId = rc.EmpReqCanId " +
                    "    LEFT JOIN RCMEmpRequest req ON rc.EmpReqId = req.EmpReqId " +
                    "    LEFT JOIN RCMACTActivity a ON ca.RcmActId = a.RcmActId " +
                    "    LEFT JOIN StageOrder so ON a.RcmActGrp = so.StageName " +
                    "    WHERE req.EmpReqStatus = 'IN_PROGRESS' " +
                    "    GROUP BY ca.EmpReqCanId, so.StageOrder, so.StageName " +
                    "), " +
                    "CurrentStage AS ( " +
                    "    SELECT * FROM ( " +
                    "    SELECT sa.*, ROW_NUMBER() OVER ( " +
                    "    PARTITION BY sa.EmpReqCanId " +
                    "    ORDER BY CASE WHEN sa.HasInProgress = 1 THEN 0 ELSE 1 END, sa.StageOrder) AS rn " +
                    "    FROM StageAgg sa) x " +
                    "    WHERE rn = 1 " +
                    ") " +
                    "SELECT " +
                    "    SUM(CASE WHEN cs.StageName = 'Assessment' THEN 1 ELSE 0 END) AS totalAssessment, " +
                    "    SUM(CASE WHEN cs.StageName = 'Offering' THEN 1 ELSE 0 END) AS totalOffering, " +
                    "    SUM(CASE WHEN cs.StageName = 'Background Check' THEN 1 ELSE 0 END) AS totalBackgroundCheck, " +
                    "    SUM(CASE WHEN cs.StageName = 'Sign Agreement' THEN 1 ELSE 0 END) AS totalSignAgreement, " +
                    "    SUM(CASE WHEN cs.StageName = 'Onboarding' THEN 1 ELSE 0 END) AS totalOnboarding " +
                    "FROM CurrentStage cs")
    DashboardPipelineResp getEmployeeRequestPipeline();
}
