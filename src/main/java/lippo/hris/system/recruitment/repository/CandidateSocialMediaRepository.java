package lippo.hris.system.recruitment.repository;

import lippo.hris.system.recruitment.entity.Candidate;
import lippo.hris.system.recruitment.entity.CandidateContact;
import lippo.hris.system.recruitment.entity.CandidateSocialMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CandidateSocialMediaRepository extends JpaRepository<CandidateSocialMedia, Long> {

    @Query(nativeQuery = true,
            value = "SELECT * FROM RCMCANCanSocMed rc " +
                    "WHERE rc.CanId = :CanId AND rc.CanSocMedMsId = :CanSocMedMsId")
    Optional<CandidateSocialMedia> findByCandidateAndCandidateSocialMediaMaster(@Param("CanId") Long CanId,
                                                                        @Param("CanSocMedMsId") Long CanSocMedMsId);

    void deleteAllByCandidate(Candidate candidate);
}
