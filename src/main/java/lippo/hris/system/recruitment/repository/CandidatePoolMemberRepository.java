package lippo.hris.system.recruitment.repository;

import lippo.hris.system.recruitment.entity.Candidate;
import lippo.hris.system.recruitment.entity.CandidatePoolMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidatePoolMemberRepository extends JpaRepository<CandidatePoolMember, Long> {

    CandidatePoolMember findByCandidateAndMainTrue(Candidate candidate);
}
