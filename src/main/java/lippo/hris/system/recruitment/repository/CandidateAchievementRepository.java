package lippo.hris.system.recruitment.repository;

import lippo.hris.system.recruitment.entity.Candidate;
import lippo.hris.system.recruitment.entity.CandidateAchievement;
import lippo.hris.system.recruitment.entity.CandidateCertification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CandidateAchievementRepository extends JpaRepository<CandidateAchievement, Long> {

    List<CandidateAchievement> findByCandidate(Candidate candidate);

    Optional<CandidateAchievement> findByCandidateAndAchievement(Candidate candidate, String achievement);

    void deleteAllByCandidate(Candidate candidate);
}
