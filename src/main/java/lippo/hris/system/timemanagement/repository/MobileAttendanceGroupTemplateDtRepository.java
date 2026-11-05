package lippo.hris.system.timemanagement.repository;

import lippo.hris.system.timemanagement.entity.MobileAttendanceGroupTemplate;
import lippo.hris.system.timemanagement.entity.MobileAttendanceGroupTemplateDt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MobileAttendanceGroupTemplateDtRepository extends JpaRepository<MobileAttendanceGroupTemplateDt, Long> {

    List<MobileAttendanceGroupTemplateDt> findByMobileAttendanceGroupTemplate(MobileAttendanceGroupTemplate mobileAttendanceGroupTemplate);
    MobileAttendanceGroupTemplateDt findByMobileAttendanceGroupTemplateAndTemplateCode(MobileAttendanceGroupTemplate mobileAttendanceGroupTemplate, String templateCode);

    @Query(nativeQuery = true, value = "SELECT DISTINCT dt.MobileAttendTempCode " +
            "FROM TMMobileAttendGrpTempDt dt " +
            "INNER JOIN TMMobileAttendGrpTemp temp ON dt.MobileAttendGrpTempId = temp.MobileAttendGrpTempId " +
            "WHERE temp.RcmHRBPId IN (SELECT uh.RcmHRBPId FROM URMUserHRBP uh WHERE uh.UserId = " +
            "(SELECT UserId FROM URMUser WHERE UserName = :username))" )
    List<String> findAttendanceCodeByHrbp(String username);
}
