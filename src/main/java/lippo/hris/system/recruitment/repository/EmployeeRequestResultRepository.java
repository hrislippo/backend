package lippo.hris.system.recruitment.repository;

import lippo.hris.system.recruitment.entity.EmployeeRequestForm;
import lippo.hris.system.recruitment.entity.EmployeeRequestResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRequestResultRepository extends JpaRepository<EmployeeRequestResult, Long> {
    List<EmployeeRequestResult> findByEmployeeRequestForm(EmployeeRequestForm employeeRequestForm);
    Integer countByCreatedBy(String createdBy);
}
