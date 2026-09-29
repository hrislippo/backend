package lippo.hris.system.talentmanagement.service;

import lippo.hris.system.talentmanagement.entity.TalentPoolPerformance;
import lippo.hris.system.talentmanagement.entity.TalentPoolPotential;
import lippo.hris.system.talentmanagement.repository.TalentPoolPerformanceRepository;
import lippo.hris.system.talentmanagement.repository.TalentPoolPotentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TalentPoolPerformanceService {

    @Autowired
    TalentPoolPerformanceRepository talentPoolPerformanceRepository;

    public List<TalentPoolPerformance> findAllPerformance(){
        return talentPoolPerformanceRepository.findAll();
    }
}
