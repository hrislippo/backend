package lippo.hris.system.recruitment.repository;

import lippo.hris.system.recruitment.entity.Candidate;
import lippo.hris.system.recruitment.entity.CandidateContact;
import lippo.hris.system.recruitment.entity.CandidateSocialMediaMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CandidateSocialMediaMasterRepository extends JpaRepository<CandidateSocialMediaMaster, Long> {

    Optional<CandidateSocialMediaMaster> findByType(String type);
}
