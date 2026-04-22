package lippo.hris.system.recruitment.repository;

import lippo.hris.system.recruitment.entity.Candidate;
import lippo.hris.system.recruitment.entity.CandidateAchievement;
import lippo.hris.system.recruitment.entity.CandidatePublication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CandidatePublicationRepository extends JpaRepository<CandidatePublication, Long> {

    List<CandidatePublication> findByCandidate(Candidate candidate);

    Optional<CandidatePublication> findByCandidateAndPublication(Candidate candidate, String publication);

    void deleteAllByCandidate(Candidate candidate);
}
