package lippo.hris.system.authentication.repository;

import lippo.hris.system.authentication.entity.User;
import lippo.hris.system.authentication.entity.UserCandidatePool;
import lippo.hris.system.recruitment.entity.CandidatePool;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCandidatePoolRepository extends JpaRepository<UserCandidatePool, Long> {

    List<UserCandidatePool> findByUser(User user);
    UserCandidatePool findByUserAndCandidatePool(User user, CandidatePool candidatePool);
}
