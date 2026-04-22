package lippo.hris.system.recruitment.repository;

import lippo.hris.system.recruitment.entity.Candidate;
import lippo.hris.system.recruitment.entity.CandidateContact;
import lippo.hris.system.recruitment.entity.CandidateContactMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CandidateContactRepository extends JpaRepository<CandidateContact, Long> {

    @Query(nativeQuery = true,
    value = "SELECT * FROM RCMCANCanContact rc " +
            "WHERE rc.CanId = :CanId AND rc.CanContactMsId = :CanContactMsId")
    Optional<CandidateContact> findByCandidateAndCandidateContactMaster(@Param("CanId") Long CanId,
                                                                        @Param("CanContactMsId") Long CanContactMsId);

    void deleteAllByCandidate(Candidate candidate);
}
