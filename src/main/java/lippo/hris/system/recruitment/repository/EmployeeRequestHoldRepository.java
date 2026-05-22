package lippo.hris.system.recruitment.repository;

import lippo.hris.system.recruitment.entity.EmployeeRequestForm;
import lippo.hris.system.recruitment.entity.EmployeeRequestHold;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRequestHoldRepository extends JpaRepository<EmployeeRequestHold, Long> {
    List<EmployeeRequestHold> findByEmployeeRequestForm(EmployeeRequestForm employeeRequestForm);
}
