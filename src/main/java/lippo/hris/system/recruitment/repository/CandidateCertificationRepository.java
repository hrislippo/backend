package lippo.hris.system.recruitment.repository;

import lippo.hris.system.recruitment.entity.Candidate;
import lippo.hris.system.recruitment.entity.CandidateCertification;
import lippo.hris.system.recruitment.entity.CandidateSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CandidateCertificationRepository extends JpaRepository<CandidateCertification, Long> {

    List<CandidateCertification> findByCandidate(Candidate candidate);

    Optional<CandidateCertification> findByCandidateAndCertification(Candidate candidate, String certification);

    void deleteAllByCandidate(Candidate candidate);
}
