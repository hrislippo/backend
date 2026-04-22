package lippo.hris.system.recruitment.repository;

import lippo.hris.system.recruitment.entity.Candidate;
import lippo.hris.system.recruitment.entity.CandidateSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CandidateSkillRepository extends JpaRepository<CandidateSkill, Long> {

    List<CandidateSkill> findByCandidate(Candidate candidate);

    Optional<CandidateSkill> findByCandidateAndSkill(Candidate candidate, String skill);

    void deleteAllByCandidate(Candidate candidate);
}
