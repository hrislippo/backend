package lippo.hris.system.recruitment.repository;

import lippo.hris.system.recruitment.entity.Candidate;
import lippo.hris.system.recruitment.response.CandidateResp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    @Query(nativeQuery = true,
            value="SELECT * " +
                    "FROM f_SearchKeywordCandidate(:keyword, :username) " +
                    "ORDER BY CanName",
            countQuery = "SELECT COUNT(*) " +
                    "FROM f_SearchKeywordCandidate(:keyword, :username)")
    Page<CandidateResp> getCandidate(@Param("keyword") String keyword, Pageable pageable, @Param("username") String username);

    @Query(nativeQuery = true,
            value="SELECT * " +
                    "FROM RCMCANCanData rd " +
                    "WHERE CanFgShortlist = 'true' " +
                    "ORDER BY CanName")
    List<Candidate> getCandidateList();


    @Query(nativeQuery = true, value = "SELECT TOP 1 rd.CanNum " +
            "FROM RCMCANCanData rd " +
            "WHERE CanNum LIKE :prefix+'%' " +
            "ORDER BY CanNum DESC")
    Long countByCandidateNumberStartingWith(@Param("prefix") String prefix);

    @Query(nativeQuery = true, value = "SELECT TOP 1 rd.CanId " +
            "FROM RCMCANCanData rd " +
            "INNER JOIN RCMCANCanContact rc ON rd.CanId = rc.CanId " +
            "WHERE rc.CanContact = :email OR rc.CanContact = :phoneNumber")
    Long getCandidateByEmailOrPhoneNumber(@Param("email") String email, @Param("phoneNumber") String phoneNumber);
}
