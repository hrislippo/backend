package lippo.hris.system.recruitment.repository;

import lippo.hris.system.recruitment.entity.EmployeeRequestResult;
import lippo.hris.system.recruitment.entity.EmployeeRequestResultPersonalDt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRequestResultPersonalDtRepository extends JpaRepository<EmployeeRequestResultPersonalDt, Long> {

    EmployeeRequestResultPersonalDt findByEmployeeRequestResult(EmployeeRequestResult employeeRequestResult);
}
