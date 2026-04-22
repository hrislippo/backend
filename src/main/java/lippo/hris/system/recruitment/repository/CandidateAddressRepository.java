package lippo.hris.system.recruitment.repository;

import lippo.hris.system.recruitment.entity.Candidate;
import lippo.hris.system.recruitment.entity.CandidateAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CandidateAddressRepository extends JpaRepository<CandidateAddress, Long> {

    Optional<CandidateAddress> findByCandidate(Candidate candidate);

    void deleteAllByCandidate(Candidate candidate);
}
