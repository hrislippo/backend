package lippo.hris.system.talentmanagement.validation;

import lippo.hris.system.exception.BadRequestException;
import lippo.hris.system.talentmanagement.repository.TalentPoolRepository;
import lippo.hris.system.talentmanagement.request.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TalentPoolValidation {

    @Autowired
    private TalentPoolRepository talentPoolRepository;

    public void nikRequired(List<TalentPoolReq> talentPoolReq){
        for(TalentPoolReq talentPool : talentPoolReq){
            if(talentPool.getEmployeeNIK() == null || talentPool.getEmployeeNIK().isEmpty()){
                throw new BadRequestException("NIK cannot be empty");
            }
        }
    }

    public void nikDuplicate(List<TalentPoolReq> talentPoolReq){
        List<String> nikList = talentPoolReq.stream().map(TalentPoolReq::getEmployeeNIK).toList();
        boolean hasDuplicate = nikList.stream()
                .distinct()
                .count() != nikList.size();

        if(hasDuplicate){
            throw new BadRequestException("NIK cannot be duplicate");
        }

        for(String nik : nikList){
            if(talentPoolRepository.findByEmployeeNIK(nik) != null){
                throw new BadRequestException("NIK already become talent");
            }
        }
    }

    public void reasonRequired(TalentPoolReq talentPoolReq){
        if(talentPoolReq.getReason() == null || talentPoolReq.getReason().isEmpty()){
            throw new BadRequestException("Reason is required");
        }
    }
}
