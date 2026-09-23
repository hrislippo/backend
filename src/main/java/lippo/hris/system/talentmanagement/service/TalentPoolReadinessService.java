package lippo.hris.system.talentmanagement.service;

import lippo.hris.system.talentmanagement.entity.TalentPoolReadiness;
import lippo.hris.system.talentmanagement.repository.TalentPoolReadinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TalentPoolReadinessService {

    @Autowired
    TalentPoolReadinessRepository talentPoolReadinessRepository;

    public List<TalentPoolReadiness> findAllReadiness(){
        return talentPoolReadinessRepository.findAll();
    }
}
