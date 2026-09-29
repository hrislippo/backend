package lippo.hris.system.talentmanagement.service;

import lippo.hris.system.talentmanagement.entity.TalentPoolPotential;
import lippo.hris.system.talentmanagement.repository.TalentPoolPotentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TalentPoolPotentialService {

    @Autowired
    TalentPoolPotentialRepository talentPoolPotentialRepository;

    public List<TalentPoolPotential> findAllPotential(){
        return talentPoolPotentialRepository.findAll();
    }
}
