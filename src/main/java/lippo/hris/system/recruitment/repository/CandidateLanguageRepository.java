package lippo.hris.system.recruitment.repository;

import lippo.hris.system.recruitment.entity.Candidate;
import lippo.hris.system.recruitment.entity.CandidateLanguage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidateLanguageRepository extends JpaRepository<CandidateLanguage, Long> {

    List<CandidateLanguage> findByCandidate(Candidate candidate);

    void deleteAllByCandidate(Candidate candidate);
}
