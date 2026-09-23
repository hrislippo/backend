package lippo.hris.system.talentmanagement.repository;

import lippo.hris.system.talentmanagement.entity.TalentPoolReadiness;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TalentPoolReadinessRepository extends JpaRepository<TalentPoolReadiness, Long> {
    TalentPoolReadiness findByName(String name);
}
