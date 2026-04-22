package lippo.hris.system.recruitment.repository;

import lippo.hris.system.recruitment.entity.Candidate;
import lippo.hris.system.recruitment.entity.EmployeeRequestCandidate;
import lippo.hris.system.recruitment.entity.EmployeeRequestForm;
import lippo.hris.system.recruitment.entity.EmployeeRequestPIC;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRequestCandidateRepository extends JpaRepository<EmployeeRequestCandidate, Long> {

    List<EmployeeRequestCandidate> findByEmployeeRequestForm (EmployeeRequestForm employeeRequestForm);
    void deleteAllByEmployeeRequestForm (EmployeeRequestForm employeeRequestForm);
    EmployeeRequestCandidate findByEmployeeRequestFormAndCandidate (EmployeeRequestForm employeeRequestForm, Candidate candidate);
    List<EmployeeRequestCandidate> findByCandidate (Candidate candidate);
}
