package lippo.hris.system.recruitment.repository;

import lippo.hris.system.recruitment.entity.Candidate;
import lippo.hris.system.recruitment.entity.CandidateDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateDocumentRepository extends JpaRepository<CandidateDocument, Long> {

    CandidateDocument findByCandidate(Candidate candidate);
}
