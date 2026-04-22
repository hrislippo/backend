package lippo.hris.system.recruitment.repository;

import lippo.hris.system.authentication.entity.User;
import lippo.hris.system.recruitment.entity.EmployeeRequestForm;
import lippo.hris.system.recruitment.entity.EmployeeRequestPIC;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRequestPICRepository extends JpaRepository<EmployeeRequestPIC, Long> {

    List<EmployeeRequestPIC> findByEmployeeRequestForm (EmployeeRequestForm employeeRequestForm);
    void deleteAllByEmployeeRequestForm (EmployeeRequestForm employeeRequestForm);
    EmployeeRequestPIC findByEmployeeRequestFormAndUser (EmployeeRequestForm employeeRequestForm, User user);
    List<EmployeeRequestPIC> findByUser (User user);
}
