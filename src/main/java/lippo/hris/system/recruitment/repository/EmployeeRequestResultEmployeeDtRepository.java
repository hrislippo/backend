package lippo.hris.system.recruitment.repository;

import lippo.hris.system.recruitment.entity.EmployeeRequestResult;
import lippo.hris.system.recruitment.entity.EmployeeRequestResultEmployeeDt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRequestResultEmployeeDtRepository extends JpaRepository<EmployeeRequestResultEmployeeDt, Long> {
    EmployeeRequestResultEmployeeDt findByEmployeeRequestResult(EmployeeRequestResult employeeRequestResult);
}
