package lippo.hris.system.recruitment.repository;

import lippo.hris.system.recruitment.entity.Candidate;
import lippo.hris.system.recruitment.entity.CandidateBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateBlacklistRepository extends JpaRepository<CandidateBlacklist, Long> {
    CandidateBlacklist findByCandidate(Candidate candidate);
}
