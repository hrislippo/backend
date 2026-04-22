package lippo.hris.system.recruitment.repository;

import lippo.hris.system.recruitment.entity.CandidateContactMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CandidateContactMasterRepository extends JpaRepository<CandidateContactMaster, Long> {

    Optional<CandidateContactMaster> findByType(String type);
}
