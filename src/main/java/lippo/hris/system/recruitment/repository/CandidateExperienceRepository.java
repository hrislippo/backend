package lippo.hris.system.recruitment.repository;

import lippo.hris.system.recruitment.entity.Candidate;
import lippo.hris.system.recruitment.entity.CandidateExperience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidateExperienceRepository extends JpaRepository<CandidateExperience, Long> {

    List<CandidateExperience> findByCandidate(Candidate candidate);

    void deleteAllByCandidate(Candidate candidate);
}
