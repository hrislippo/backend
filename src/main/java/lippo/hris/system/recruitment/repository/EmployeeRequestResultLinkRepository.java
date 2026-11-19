package lippo.hris.system.recruitment.repository;

import lippo.hris.system.recruitment.entity.EmployeeRequestResult;
import lippo.hris.system.recruitment.entity.EmployeeRequestResultLink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRequestResultLinkRepository extends JpaRepository<EmployeeRequestResultLink, Long> {
    EmployeeRequestResultLink findByEmployeeRequestResult(EmployeeRequestResult employeeRequestResult);
    EmployeeRequestResultLink findByToken(String token);
}
