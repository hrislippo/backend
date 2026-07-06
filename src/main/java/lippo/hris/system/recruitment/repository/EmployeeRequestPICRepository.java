package lippo.hris.system.recruitment.repository;

import lippo.hris.system.authentication.entity.User;
import lippo.hris.system.recruitment.entity.EmployeeRequestForm;
import lippo.hris.system.recruitment.entity.EmployeeRequestPIC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRequestPICRepository extends JpaRepository<EmployeeRequestPIC, Long> {

    List<EmployeeRequestPIC> findByEmployeeRequestForm (EmployeeRequestForm employeeRequestForm);
    void deleteAllByEmployeeRequestForm (EmployeeRequestForm employeeRequestForm);
    EmployeeRequestPIC findByEmployeeRequestFormAndUser (EmployeeRequestForm employeeRequestForm, User user);
    List<EmployeeRequestPIC> findByUser (User user);

    @Query(nativeQuery = true,
            value = "SELECT COUNT(1) " +
                    "FROM RCMEmpRequest req " +
                    "INNER JOIN RCMEmpReqPIC pic ON req.EmpReqId = pic.EmpReqId " +
                    "INNER JOIN URMUser usr ON pic.UserId = usr.UserId " +
                    "WHERE req.EmpReqStatus = 'IN_PROGRESS' AND usr.UserName = :nik")
    Integer countByUserAndInProgress(@Param("nik") String username);
}
