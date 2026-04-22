package lippo.hris.system.recruitment.repository;

import lippo.hris.system.recruitment.entity.Candidate;
import lippo.hris.system.recruitment.entity.CandidateEducation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidateEducationRepository extends JpaRepository<CandidateEducation, Long> {

    List<CandidateEducation> findByCandidate(Candidate candidate);

    void deleteAllByCandidate(Candidate candidate);
}
