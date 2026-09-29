package lippo.hris.system.talentmanagement.repository;

import lippo.hris.system.talentmanagement.entity.TalentPool;
import lippo.hris.system.talentmanagement.entity.TalentPoolRequest;
import lippo.hris.system.talentmanagement.entity.TalentPoolRequestDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TalentPoolRequestDetailRepository extends JpaRepository<TalentPoolRequestDetail, Long> {

    TalentPoolRequestDetail findByHeaderAndTalent(TalentPoolRequest talentPoolRequest, TalentPool talentPool);
    List<TalentPoolRequestDetail> findByHeader(TalentPoolRequest talentPoolRequest);
    void deleteByHeaderAndTalent(TalentPoolRequest talentPoolRequest, TalentPool talentPool);
}
